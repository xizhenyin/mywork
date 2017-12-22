package com.cnpc.dj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cnpc.dj.party.mapper")
public class Management1Application {

	public static void main(String[] args) {
		SpringApplication.run(Management1Application.class, args);
	}
}
