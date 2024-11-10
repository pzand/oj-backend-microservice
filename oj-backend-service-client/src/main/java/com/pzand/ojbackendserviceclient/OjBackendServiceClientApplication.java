package com.pzand.ojbackendserviceclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
//@MapperScan("com.pzand.ojbackenduserservice.mapper")
//@EnableScheduling
//@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
//@ComponentScan("com.pzand")
//@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.pzand.ojbackendserviceclient.service")
public class OjBackendServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjBackendServiceClientApplication.class, args);
    }

}