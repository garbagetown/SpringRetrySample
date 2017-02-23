package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "retry")
@Data
public class RetryProperties {

	private String parallelism;
	private int maxAttempts;
	private int delay;
}
