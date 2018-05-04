package com.powermilk;

import com.powermilk.service.CorrectQuestionValidatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackageClasses = {
        CorrectQuestionValidatorService.class,
})
@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("application.properties")
public class QuizApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }
}
