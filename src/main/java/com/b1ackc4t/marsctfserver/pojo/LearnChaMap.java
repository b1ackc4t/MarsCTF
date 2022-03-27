package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class LearnChaMap {
    private Integer lid;
    private Integer cid;

    public LearnChaMap() {}

    public LearnChaMap(Integer lid, Integer cid) {
        this.lid = lid;
        this.cid = cid;
    }
}
