package com.b1ackc4t.marsctfserver.util;

import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.pojo.Config;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FrpUtil {

    public static void flushFrpConfig(String frpcConfigTemp) {
        String frpcConfig = frpcConfigTemp;
        String directTemp = "\n\n[direct_%s_tcp]\n" +
                            "type = tcp\n" +
                            "local_ip = %s\n" +
                            "local_port = %s\n" +
                            "remote_port = %s\n" +
                            "\n\n[direct_%s_udp]\n" +
                            "type = udp\n" +
                            "local_ip = %s\n" +
                            "local_port = %s\n" +
                            "remote_port = %s\n";


    }
}
