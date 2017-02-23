package com.example;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
@EnableRetry
public class RetrySampleApplication {
	
	private final RetryProperties retryProperties;
	private final RetryService service;
	
	public static void main(String[] args) {
		SpringApplication.run(RetrySampleApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner() {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", retryProperties.getParallelism());
		return arg -> {
			IntStream.range(0, 100).boxed().parallel().forEach(i -> service.execute(i));
		};
	}
}
