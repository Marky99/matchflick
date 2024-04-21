package com.marky.matchflick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    enableDefaultTransactions = false,
    basePackages = {"com.marky.matchflick.repository"}
)
public class GlobalConfiguration {

}

