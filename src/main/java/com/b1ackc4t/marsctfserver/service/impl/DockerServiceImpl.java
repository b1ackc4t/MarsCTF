package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChallengeMapper;
import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.dao.DockerContainerMapper;
import com.b1ackc4t.marsctfserver.pojo.*;
import com.b1ackc4t.marsctfserver.service.DockerService;
import com.b1ackc4t.marsctfserver.service.FrpService;
import com.b1ackc4t.marsctfserver.util.CommonUtil;
import com.b1ackc4t.marsctfserver.util.DockerPullImageCallback;
import com.b1ackc4t.marsctfserver.util.DockerUtil;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class DockerServiceImpl implements DockerService {

    final DockerContainerMapper dockerContainerMapper;
    final ConfigMapper configMapper;
    final FrpService frpService;
    final ChallengeMapper challengeMapper;

    @Autowired
    public DockerServiceImpl(DockerContainerMapper dockerContainerMapper, ConfigMapper configMapper, FrpService frpService, ChallengeMapper challengeMapper) {
        this.dockerContainerMapper = dockerContainerMapper;
        this.configMapper = configMapper;
        this.frpService = frpService;
        this.challengeMapper = challengeMapper;
    }

    /**
     * 用户创建容器
     * @param cid
     * @param uid
     * @return
     * @throws InterruptedException
     */
    @Override
    public ReturnRes createContainerForUser(Integer cid, Integer uid) throws InterruptedException, IOException {
        Config config = configMapper.selectDockerConfig();
        int allCount = dockerContainerMapper.selectCount();
        if (allCount >= config.getMaxContainerCount()) {
            return new ReturnRes(false, "平台容器数量过多，请稍后再尝试");
        }
        int userCsCount = dockerContainerMapper.selectCountByUid(uid);
        if (userCsCount >= config.getUserMaxContainer()) {
            return new ReturnRes(false, "用户最多同时开启" + config.getUserMaxContainer() + "个容器");
        }
        Challenge challenge = challengeMapper.selectContainerInfoByCid(cid);
        int testPort = new Random().nextInt(config.getDockerMaxPort() - config.getDockerMinPort());
        while (dockerContainerMapper.selectPort(config.getDockerMinPort() + testPort) > 0) {
            testPort = new Random().nextInt(config.getDockerMaxPort() - config.getDockerMinPort());
        }
        Integer dstPort = config.getDockerMinPort() + testPort;
        // 创建容器
        DockerContainer dockerContainer = new DockerContainer();
        dockerContainer.setName(String.format("%s-%s", String.valueOf(uid), UUID.randomUUID()));
        dockerContainer.setSrcPort(challenge.getSrcPort());
        dockerContainer.setDstPort(dstPort);
        dockerContainer.setCpuLimit(challenge.getCpuLimit());
        dockerContainer.setMemLimit(challenge.getMemLimit());
        dockerContainer.setImageName(challenge.getImageName());
        dockerContainer.setAddCount(0);
        dockerContainer.setAddTime(0);
        if (challenge.getIsDynamicFlag()) {
            dockerContainer.setFlag(String.format("%s{%s}", challenge.getFlagPrefix(), UUID.randomUUID()));
        } else {
            dockerContainer.setFlag(null);
        }
        CreateContainerResponse createContainerResponse = DockerUtil.createContainer(dockerContainer);

        // 启动容器
        Date startTime = new Date();
        dockerContainer.clearExtraProp();
        dockerContainer.setStartTime(startTime);
        dockerContainer.setUid(uid);
        dockerContainer.setCid(cid);
        dockerContainer.setStatus("running");
        dockerContainer.setContainerId(createContainerResponse.getId());
        if (dockerContainerMapper.insert(dockerContainer) > 0) {
            DockerUtil.startContainer(dockerContainer.getContainerId());
        }
        frpService.flushFrpConfig();
        ContainerView containerView = new ContainerView();
        containerView.setUrl("http://" + config.getRetDomain() + ":" + dstPort + "/");
        containerView.setEndTime(new Date(startTime.getTime() + config.getDockerTime() * 1000));
        containerView.setCount(config.getAddTimeCount());
        return new ReturnRes(true, containerView, "容器开启成功");
    }

    /**
     * 管理员拉取镜像
     * @param imageName
     * @return
     * @throws InterruptedException
     */
    @Override
    public ReturnRes addDockerForCha(String imageName, Integer cid) throws InterruptedException {
        String[] imageArr = DockerUtil.resolveImageName(imageName);
        DockerUtil.pullImage(imageArr[0], new DockerPullImageCallback(cid, imageArr[2]));
        return null;
    }



    /**
     * 管理员删除镜像
     * @param imageName
     * @return
     */
    @Override
    public ReturnRes removeDockerForCha(String imageName, Integer cid) throws IOException {
        removeContainerForCha(cid);
        DockerUtil.removeImage(imageName);
        return null;
    }

    /**
     * 用户释放所有容器
     * @param uid
     * @return
     */
    @Override
    public ReturnRes removeContainerForUser(Integer uid) throws IOException {
        List<String> containerIds = dockerContainerMapper.selectContainerIdByUid(uid);
        dockerContainerMapper.deleteByUid(uid);
        for (String containerId : containerIds) {
//            DockerUtil.stopContainer(containerId);
            DockerUtil.removeContainer(containerId);
        }
        frpService.flushFrpConfig();
        return null;
    }

    @Override
    public ReturnRes removeContainerForCha(Integer cid) throws IOException {
        List<String> containerIds = dockerContainerMapper.selectContainerIdByCid(cid);
        dockerContainerMapper.deleteByCid(cid);
        for (String containerId : containerIds) {
//            DockerUtil.stopContainer(containerId);
            DockerUtil.removeContainer(containerId);
        }
        frpService.flushFrpConfig();
        return null;
    }

    @Override
    public ReturnRes getContainerByCidUid(Integer cid, Integer uid) {
        DockerContainer dockerContainer = dockerContainerMapper.selectContainerByCidUid(cid, uid);
        if (dockerContainer != null) {
            Config config = configMapper.selectDockerConfig();
            ContainerView containerView = new ContainerView();
            containerView.setUrl("http://" + config.getRetDomain() + ":" + dockerContainer.getDstPort() + "/");
            containerView.setEndTime(new Date(dockerContainer.getStartTime().getTime() + config.getDockerTime() * 1000 + dockerContainer.getAddTime() * 1000));
            containerView.setCount(config.getAddTimeCount() - dockerContainer.getAddCount());
            return new ReturnRes(true, containerView, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes removeContainerByCidUid(Integer cid, Integer uid) throws IOException {
        String containerId = dockerContainerMapper.selectContainerIdByCidUid(cid, uid);
        if (containerId != null) {
            dockerContainerMapper.deleteByUidCid(uid, cid);
//            DockerUtil.stopContainer(containerId);
            DockerUtil.removeContainer(containerId);
            frpService.flushFrpConfig();
            return new ReturnRes(true, "销毁成功");
        }
        return new ReturnRes(false, "销毁失败");
    }

    @Override
    public ReturnRes getContainerByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DockerContainer> dockerContainers = dockerContainerMapper.selectAllContainerForAdmin();
        Integer time = configMapper.selectTimeConfig();
        if (dockerContainers != null) {
            for (DockerContainer dockerContainer : dockerContainers) {
                dockerContainer.setEndTime(new Date(dockerContainer.getStartTime().getTime() + time * 1000 + dockerContainer.getAddTime() * 1000));
            }
            return new ReturnRes(true, new PageInfo<>(dockerContainers), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes removeContainerByConId(String id) throws IOException {
        if (dockerContainerMapper.deleteById(id) > 0) {
            DockerUtil.removeContainer(id);
            frpService.flushFrpConfig();
            return new ReturnRes(true, "销毁成功");
        }
        return new ReturnRes(false, "销毁失败");
    }

    @Override
    public ReturnRes addTimeForAdmin(Integer uid, Integer cid, Integer second) {
        Integer addTime = dockerContainerMapper.selectAddTimeByUidCid(uid, cid);
        if (dockerContainerMapper.updateTimeByUidCid(addTime + second, uid, cid) > 0) {
            return new ReturnRes(true, "延时成功");
        }
        return new ReturnRes(false, "延时失败");
    }

    @Override
    public ReturnRes addTimeForUser(Integer uid, Integer cid) {
        DockerContainer dockerContainer = dockerContainerMapper.selectAddTimeForUserByUidCid(uid, cid);
        Config config = configMapper.selectAddTimeForUserConfig();
        if (dockerContainer.getAddCount() >= config.getAddTimeCount()) {
            return new ReturnRes(false, "添加时长的次数已达到最大值");
        }
        if (dockerContainerMapper.updateTimeForUserByUidCid(dockerContainer.getAddTime() + config.getAddTimeNum(), dockerContainer.getAddCount() + 1, uid, cid) > 0) {
            return new ReturnRes(true, "延时成功");
        }
        return new ReturnRes(false, "延时失败");
    }

    @Override
    public ReturnRes searchContainerByPage(String key, String value, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"uid", "cname", "dstPort", "startTime", "endTime", "flag"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<DockerContainer> dockerContainers = dockerContainerMapper.selectSearchContainerForAdmin(key, "%" + value + "%");
            Integer time = configMapper.selectTimeConfig();
            if (dockerContainers != null) {
                for (DockerContainer dockerContainer : dockerContainers) {
                    dockerContainer.setEndTime(new Date(dockerContainer.getStartTime().getTime() + time * 1000 + dockerContainer.getAddTime() * 1000));
                }
                return new ReturnRes(true, new PageInfo<>(dockerContainers), "查询成功");
            }
            return new ReturnRes(false, "查询失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }

    }
}
