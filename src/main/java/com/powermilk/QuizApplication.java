package com.powermilk;

import com.powermilk.service.CorrectQuestionValidatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {
		CorrectQuestionValidatorService.class,
})
@SpringBootApplication
@EnableAutoConfiguration
public class QuizApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}
}
