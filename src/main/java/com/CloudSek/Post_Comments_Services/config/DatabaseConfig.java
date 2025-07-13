package com.CloudSek.Post_Comments_Services.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.CloudSek.Post_Comments_Services.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfig {
}