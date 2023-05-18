package com.example.reggie.config;

import com.example.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * springboot默认将静态资源放在static，但是我们放在了backend和front里面，所以要编写一个配置类来使spring boot放行这两个文件夹中的静态资源
 *
 * @author yangyibufeng
 * @date 2023/4/20
 */
@Slf4j
@Configuration//要注解说明这是一个配置类
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * @param registry:
     * @return void:
     * @author yangyibufeng
     * @description 设置静态资源映射
     * @date 2023/4/20 22:00
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 在项目启动的时候就被调用
     * 扩展mvc框架的消息转化器
     *自己新建一个消息转化器，将其加入到mvc框架的转换器集合中，并且放在第一位确保能够生效
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter MessageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用jackson将Java转换为json
        MessageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中去
        converters.add(0,MessageConverter);
    }
}