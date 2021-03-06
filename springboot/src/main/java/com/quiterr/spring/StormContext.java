package com.quiterr.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * 单例类，创建SpringBoot环境，提供获取bean的方法
 */
public class StormContext {

    private static final Logger logger = LoggerFactory.getLogger(StormContext.class);

    private volatile static StormContext stormContext;
    private ApplicationContext context;

    public synchronized static StormContext createApplicationContext() {
        logger.info("Enter createApplicationContext ");
        if (stormContext == null) {
            ApplicationContext appContext = null;
            try {
                SpringApplicationBuilder application = new SpringApplicationBuilder(SpringApplication.class)
                        .web(false);
                application.run();
                appContext = application.context();
                stormContext = new StormContext();
                stormContext.setApplicationContext(appContext);
            } catch (Exception ex) {
                logger.error("Could not create Spring ApplicationContext due to ", ex);
            }
        }
        logger.info("Got the Storm Context ");
        return stormContext;
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * Sets application context.
     *
     * @param appContext the app context
     */
    public void setApplicationContext(ApplicationContext appContext) {
        this.context = appContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name the name
     * @return the bean
     */
    public Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

}
