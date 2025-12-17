package com.KayraAtalay.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // catch the request
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    // take Authorization info from header
                    String authHeader = attributes.getRequest().getHeader("Authorization");

                    if (authHeader != null) {
                        // put the header to feign request
                        template.header("Authorization", authHeader);
                    }
                }
            }
        };
    }
}