package com.component;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.component.demo")
public class EasyexcelComponentApplication {

    public static void main( String[] args ) {
        SpringApplication.run(EasyexcelComponentApplication.class, args);
    }

}
