package org.travelling.ticketer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.travelling.ticketer.business.DelaySimulatorInterceptor;

@EnableWebMvc
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${application.allowed.origin}")
    private String allowedOrigin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DelaySimulatorInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedOrigin);
    }
}
