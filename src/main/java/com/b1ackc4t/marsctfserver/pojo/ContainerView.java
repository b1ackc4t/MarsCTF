package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ContainerView {
    private Date endTime;
    private String url;
    private Integer count;
}
