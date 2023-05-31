package com.krab.burger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.krab.burger.mapper")
@EnableScheduling
public class BurgerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BurgerApplication.class, args);
    }
}
