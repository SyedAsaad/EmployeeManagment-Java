package com.logex.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration

//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {






  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }




  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    File directory = new File (".");
//    String absPath = directory.getAbsolutePath();
//
//    String readPath =  String.format("%sstudentImages/", absPath.replace("\\", "/").replace(".", ""));
//    String filePath =  String.format("%sclaimForms/", absPath.replace("\\", "/").replace(".", ""));
//    String formatted = readPath;
//    String formattedPath = filePath;
//
//      registry.addResourceHandler("/studentImage/**").addResourceLocations(String.format("file:///%s", formatted));
//    registry.addResourceHandler("/claimForms/**").addResourceLocations(String.format("file:///%s", formattedPath));

  }



}
