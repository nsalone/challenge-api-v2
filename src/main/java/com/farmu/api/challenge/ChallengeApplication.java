package com.farmu.api.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableJpaAuditing
//@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class ChallengeApplication {

    public static final String SERVICE_NAME = "challenge";

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

}
