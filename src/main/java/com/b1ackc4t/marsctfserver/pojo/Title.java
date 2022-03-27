package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class Title {
    private String title;
    private Integer level;
    private Integer minScore;
    private Integer maxScore;

    public Title(String title, Integer level, Integer minScore, Integer maxScore) {
        this.title = title;
        this.level = level;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }
}
