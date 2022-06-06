package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;

import java.io.IOException;

public interface DockerService {
    public ReturnRes createContainerForUser(Integer cid, Integer uid) throws InterruptedException, IOException;
    public ReturnRes addDockerForCha(String imageName, Integer cid) throws InterruptedException;
    public ReturnRes removeDockerForCha(String imageName, Integer cid) throws IOException;
    public ReturnRes removeContainerForUser(Integer uid) throws IOException;
    public ReturnRes removeContainerForCha(Integer cid) throws IOException;
    public ReturnRes getContainerByCidUid(Integer cid, Integer uid);
    public ReturnRes removeContainerByCidUid(Integer cid, Integer uid) throws IOException;
    public ReturnRes addTimeForUser(Integer uid, Integer cid);

    public ReturnRes getContainerByPage(int pageNum, int pageSize);
    public ReturnRes removeContainerByConId(String id) throws IOException;
    public ReturnRes addTimeForAdmin(Integer uid, Integer cid, Integer second);
    ReturnRes searchContainerByPage(String key, String value, int pageNum, int pageSize);
    ReturnRes check();

}
