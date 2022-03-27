package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DockerContainer {
    private String containerId;
    private Integer uid;
    private Integer cid;
    private Date startTime;
    private String status;
    private String name;
    private Integer dstPort;
    private String flag;
    private Integer addTime;
    private Integer addCount;

    private String imageName;
    private Integer srcPort;
    private Integer no;
    private String uname;
    private String cname;
    private Double cpuLimit;
    private Integer memLimit;
    private Date endTime;

    public DockerContainer clearExtraProp() {
        this.imageName = null;
        this.srcPort = null;
        this.cpuLimit = null;
        this.memLimit = null;
        return this;
    }
}
