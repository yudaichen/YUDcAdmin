package org.ydc.system.system.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//
@Configuration
public class MystaticConfig implements WebMvcConfigurer {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将路径/static/** 映射到 /static 下的文件
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
