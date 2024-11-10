package com.pzand.ojbackendjudgeservice;

import com.pzand.ojbackendjudgeservice.rabbitmq.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.pzand")
//@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.pzand.ojbackendserviceclient.service")
public class OjBackendJudgeServiceApplication {

	public static void main(String[] args) {
		InitRabbitMq.init();
		SpringApplication.run(OjBackendJudgeServiceApplication.class, args);
	}
}
