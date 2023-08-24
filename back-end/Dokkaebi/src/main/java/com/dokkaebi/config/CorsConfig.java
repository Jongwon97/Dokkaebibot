package com.dokkaebi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		// .allowedOrigins("https://i9a302.p.ssafy.io")
		// .allowedOrigins("http://127.0.0.1:3000","http://localhost:3000", "https://i9a302.p.ssafy.io")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowedHeaders("*");
		// .allowCredentials(true);
	}
	
}
