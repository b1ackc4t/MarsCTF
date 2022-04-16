package com.b1ackc4t.marsctfserver.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

// 重定向所有错误页
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[1];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
//        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

//        registry.addError(errorPages);
        registry.addErrorPages(errorPages);
    }
}
