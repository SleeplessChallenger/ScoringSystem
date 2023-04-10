package com.initialchecks.process.beanloader;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BeanLoader {

    private final ApplicationContext applicationContext;

    public <T> T getBeanFromApplicationContext(String beanName, Class<T> klass) {
        return applicationContext.getBean(beanName, klass);
    }
}
