package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class ChaTagMap {
    private Integer cid;
    private String tgname;

    public ChaTagMap() {}

    public ChaTagMap(Integer cid, String tgname) {
        this.cid = cid;
        this.tgname = tgname;
    }
}
