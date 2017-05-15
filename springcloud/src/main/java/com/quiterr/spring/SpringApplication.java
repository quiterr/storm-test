package com.quiterr.spring;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * @author agarg
 *         This is the main Configuration Class where all Spring Configurations are defined.
 *         Equivalent to spring-context.xml if using Spring XML configuration
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.chinamobile.iot")
public class SpringApplication {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        propertySourcesPlaceholderConfigurer.setNullValue("@null");
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("bootstrap.yml"));
        yaml.afterPropertiesSet();
        Properties prop = yaml.getObject();
        propertySourcesPlaceholderConfigurer.setProperties(prop);
        return propertySourcesPlaceholderConfigurer;
    }
}
