package com.millie.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication: Spring Boot 애플리케이션임을 선언하고 자동 설정을 활성화합니다.
@SpringBootApplication
// 이 클래스는 Spring Boot 애플리케이션의 진입점이며, Hexagonal Architecture의 최상위 모듈 역할을 합니다.
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}
