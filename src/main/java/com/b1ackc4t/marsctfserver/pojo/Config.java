package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class Config {
    private Integer dockerMinPort;  //映射的最小端口
    private Integer dockerMaxPort;
    private String frpsIp;
    private String frpcConfig;
    private String frpcApiHost;
    private String frpcApiPort;
    private String frpcApiUser;
    private String frpcApiPass;
    private Integer dockerTime; // docker容器的持续时间
    private Integer userMaxContainer;// 单个用户最多同时开启容器的数量
    private Integer maxContainerCount; // 平台最大容器数量
    private Integer addTimeCount;   // 用户最大续期次数
    private Integer addTimeNum; // 每次容器续期的时长
    private String dockerHost;
    private String dockerRegistry;
}
