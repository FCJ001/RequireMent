package com.byd.msp.requirement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.byd.xjsy.common.feign")
@MapperScan("com.byd.msp.requirement.**.mapper")
public class RequireMentApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequireMentApplication.class, args);
    }
}

