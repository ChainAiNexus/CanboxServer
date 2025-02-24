package com.ai.cn;

import com.cxytiandi.encrypt.springboot.annotation.EnableEncrypt;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Kires
 */
@SpringBootApplication(exclude = JndiDataSourceAutoConfiguration.class)
@MapperScan("com.nft.cn.dao")
@EnableScheduling
@EnableEncrypt
public class CanBoxUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanBoxUserApplication.class, args);
    }






}
