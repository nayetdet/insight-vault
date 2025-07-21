package io.github.nayetdet.insightvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InsightVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsightVaultApplication.class, args);
    }

}
