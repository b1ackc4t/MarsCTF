package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class ChaTagMap {
    private Integer cid;
    private Integer tgid;
    private String tgname;

    public ChaTagMap() {}

    public ChaTagMap(Integer cid, Integer tgid) {
        this.cid = cid;
        this.tgid = tgid;
    }
}
