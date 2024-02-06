package org.example.spartatodoapp.config;

import org.example.spartatodoapp.filter.AuthFilter;
import org.example.spartatodoapp.jwt.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(jwtUtil()));
        registrationBean.addUrlPatterns(
                "/api/todo/*",
                "/todos/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
