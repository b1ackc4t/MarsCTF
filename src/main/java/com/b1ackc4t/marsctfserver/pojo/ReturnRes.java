package com.b1ackc4t.marsctfserver.pojo;

import lombok.Data;

@Data
public class ReturnRes {
    private Boolean flag;
    private Object data;
    private String msg;

    public ReturnRes(boolean flag) {
        this.flag = flag;
    }

    public ReturnRes(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public ReturnRes(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public ReturnRes(boolean flag, Object data, String msg) {
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }



}
