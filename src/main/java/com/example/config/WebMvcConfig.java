package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dir = "upload-photos";
        Path dr = Paths.get(dir);
        String drFile = dr.toFile().getAbsolutePath();

//        registry.addResourceHandler("/webjars/**","/img/**","/fontawesome/**","/webfonts/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/","classpath:/static/img/","classpath:/static/fontawesome/","classpath:/static/webfonts/");
        registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/"+ dir +"/**")
                .addResourceLocations("file:/"+drFile +"/");
    }



}
