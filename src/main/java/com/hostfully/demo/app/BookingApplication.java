package com.hostfully.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>
 * after spring 2.4, we can set active profile only with environment variable or jvm args
 * </p>
 *
 * @author recepb
 */

@EntityScan("com.hostfully.demo")
@EnableJpaRepositories("com.hostfully.demo")
@SpringBootApplication(scanBasePackages = "com.hostfully.demo")
class BookingApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        final StandardEnvironment environment = new StandardEnvironment();
        environment.addActiveProfile("dev");

        final SpringApplication springApplication = new SpringApplication(BookingApplication.class);
        springApplication.setEnvironment(environment);
        springApplication.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BookingApplication.class);
    }

}
