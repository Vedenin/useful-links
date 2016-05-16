package com.github.vedenin.spring_test;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * Created by vedenin on 16.05.16.
 */
@Configuration
public class TestConfig {
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        System.out.println("dispatcherServlet");
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new DispatcherServlet(), "/");
        registration.setAsyncSupported(true);
        return registration;
    }

    @Bean
    public Filter compositeFilter() {
        System.out.println("compositeFilter");
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.setFilters(Collections.singletonList(new TestFilter()));
        return compositeFilter;
    }
}
