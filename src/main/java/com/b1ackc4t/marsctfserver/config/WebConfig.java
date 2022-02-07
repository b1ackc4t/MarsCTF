package com.b1ackc4t.marsctfserver.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 配置消息转换器 更换解析对象的工具为fastjson
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //全局时间配置
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //在convert中添加配置信息.
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(0,fastConverter);
    }

    /**
     * 允许跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(36000)
                .allowedHeaders("*");

    }

    /**
     * 设置Cookie的SameSite为None
     * 谷歌系列浏览器新加的属性 在跨域情况下默认为lax 限制了cookie传输
     * 火狐系列浏览器可以不需要samesite
     */
//    @Bean
//    public TomcatContextCustomizer sameSiteCookiesConfig() {
//        return context -> {
//            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
//            context.setCookieProcessor(cookieProcessor);
//        };
//    }
}
