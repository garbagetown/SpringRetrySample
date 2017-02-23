package com.example;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RetryService {
	
	@Retryable(value = IllegalArgumentException.class, 
			maxAttemptsExpression = "#{@retryProperties.maxAttempts}", 
			backoff = @Backoff(delayExpression = "#{@retryProperties.delay}"))
	public void execute(Integer i) {
		log.debug("execute: " + i);
		externalService(i);
	}
	
	private void externalService(Integer i) {
		if (Math.random() * 10 < 1) {
			throw new IllegalArgumentException(i.toString());
		}
	}
	
	@Recover
	public void recover(IllegalArgumentException e) {
		log.warn(e.getMessage());
	}
}
