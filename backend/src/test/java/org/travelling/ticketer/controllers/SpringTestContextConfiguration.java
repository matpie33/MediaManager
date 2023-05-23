package org.travelling.ticketer.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("org.travelling.ticketer")
public class SpringTestContextConfiguration {

    @Bean
    public Gson createGson (){
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }
}
