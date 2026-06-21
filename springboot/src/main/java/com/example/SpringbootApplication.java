package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableWebMvc // 启用 Spring MVC 的配置
@MapperScan("com.example.mapper")
public class SpringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // 所有请求路径
				.allowedOrigins("http://localhost:5174")  // 允许的 IP
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的 HTTP 方法
				.allowedHeaders("*")  // 允许的请求头
				.allowCredentials(true); // 是否允许携带凭据
		//"http://26.15.60.248", "http://26.88.146.9","http://26.32.160.202",
	}
}
