package com.example.testcontainers;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

import java.util.List;

public class CloseParentContextCustomizerFactory implements ContextCustomizerFactory {
    @Override
    public ContextCustomizer createContextCustomizer(
            Class<?> testClass,
            List<ContextConfigurationAttributes> configAttributes) {
        return (context, mergedConfig) -> context.addApplicationListener((ContextClosedEvent event) -> {
            ConfigurableApplicationContext parentContext = (ConfigurableApplicationContext)event.getApplicationContext().getParent();
            if (parentContext != null && parentContext.getId().equals("bootstrap"))
                parentContext.close();
        });
    }
}
