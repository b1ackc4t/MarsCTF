package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class LearnTagMap {
    private Integer lid;
    private Integer tgid;

    public LearnTagMap() {}

    public LearnTagMap(Integer lid, Integer tgid) {
        this.lid = lid;
        this.tgid = tgid;
    }
}
