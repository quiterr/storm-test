package com.quiterr.spring;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * @author agarg
 *         This is the main Configuration Class where all Spring Configurations are defined.
 *         Equivalent to spring-context.xml if using Spring XML configuration
 */
@Configuration
@ComponentScan(basePackages = {"com.quiterr"})
public class SpringApplication {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        propertySourcesPlaceholderConfigurer.setNullValue("@null");
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        yaml.afterPropertiesSet();
        Properties prop = yaml.getObject();
        propertySourcesPlaceholderConfigurer.setProperties(prop);
        return propertySourcesPlaceholderConfigurer;
    }
}
