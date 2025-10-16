package com.app.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    // buat Bean
    @Bean
    public String appPesan(){
        return "Aplikasi SpringBoot Dependensi...";
    }

    @Bean
    @Primary
    public String appPesan1(){
        return "Aplikasi SpringBoot Dependensi123...";
    }
}
