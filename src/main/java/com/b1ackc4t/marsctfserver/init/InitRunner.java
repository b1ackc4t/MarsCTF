package com.b1ackc4t.marsctfserver.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * CommandLineRunner接口会在所有Spring Beans初始化之后，SpringApplication.run()之前执行
 * 初始化一些必须的文件夹
 */
@Component
@Order(1)
public class InitRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
