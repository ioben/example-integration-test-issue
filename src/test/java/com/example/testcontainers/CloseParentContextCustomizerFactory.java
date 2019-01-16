package com.example.testcontainers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

import java.util.List;

public class CloseParentContextCustomizerFactory implements ContextCustomizerFactory {
    @Override
    public ContextCustomizer createContextCustomizer(
            Class<?> testClass,
            List<ContextConfigurationAttributes> configAttributes) {
        return new ContextCustomizer() {
            @Override
            public void customizeContext(ConfigurableApplicationContext context,
                                         MergedContextConfiguration mergedConfig) {
                context.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
                    @Override
                    public void onApplicationEvent(ContextClosedEvent event) {
                        ApplicationContext parentContext = event.getApplicationContext().getParent();
                        if (parentContext.getId().equals("bootstrap"))
                            ((ConfigurableApplicationContext) parentContext).close();
                    }
                });
            }
        };
    }
}
