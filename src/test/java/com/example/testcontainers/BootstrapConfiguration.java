package com.example.testcontainers;

import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

public class BootstrapConfiguration {
    @Bean(destroyMethod = "stop")
    public GenericContainer dockerComposeContainer(
            ConfigurableEnvironment environment) {

        GenericContainer container = new GenericContainer("hyness/spring-cloud-config-server");
        container.addExposedPort(8888);

        container.addFileSystemBind("config", "/root/config", BindMode.READ_ONLY);
        container.addEnv("SPRING_PROFILES_ACTIVE", "native");
        container.addEnv("SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCHLOCATIONS", "file:///root/config");

        container.start();

        return container;
    }

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator(
            GenericContainer container,
            ConfigClientProperties clientProperties) {
        clientProperties.setUri(new String[] {
                "http://" + container.getContainerIpAddress() + ":" + container.getMappedPort(8888)
        });
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(clientProperties);
        return configServicePropertySourceLocator;
    }
}


