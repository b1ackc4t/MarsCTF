package com.b1ackc4t.marsctfserver;

import com.b1ackc4t.marsctfserver.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import(SpringUtil.class)
@SpringBootApplication
@EnableScheduling
public class MarsctfServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarsctfServerApplication.class, args);
    }

}
