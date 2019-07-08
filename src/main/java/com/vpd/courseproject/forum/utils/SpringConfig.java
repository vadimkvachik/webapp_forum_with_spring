package com.vpd.courseproject.forum.utils;

import com.vpd.courseproject.forum.interceptors.SessionLocaleInterceptor;
import com.vpd.courseproject.forum.interceptors.UserChangeConditionInterceptor;
import com.vpd.courseproject.forum.interceptors.UserNewMessagesInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.vpd.courseproject.forum")
@PropertySource("classpath:forum.properties")
public class SpringConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionLocaleInterceptor());
        registry.addInterceptor(userChangeConditionInterceptor());
        registry.addInterceptor(userNewMessagesInterceptor());
    }

    @Bean
    SessionLocaleInterceptor sessionLocaleInterceptor() {
        return new SessionLocaleInterceptor();
    }

    @Bean
    UserChangeConditionInterceptor userChangeConditionInterceptor() {
        return new UserChangeConditionInterceptor();
    }

    @Bean
    UserNewMessagesInterceptor userNewMessagesInterceptor() {
        return new UserNewMessagesInterceptor();
    }

}
