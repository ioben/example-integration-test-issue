package com.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = { ExampleApplication.class, ExampleApplicationTests.OutputterConfiguration.class })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExampleApplicationTests {
    @Autowired
    ExampleApplication.Configuration configuration;

    @Autowired
    ExampleApplication.Outputter outputter;

    @Test
    public void contextLoads() {

    }

    @Test
    public void whenNoConfigIsSet_OutputsDefaultMessage() {
        Assert.assertEquals("Hello, World!", ((OutputterConfiguration.CachingOutputter) outputter).getLastMessage());
    }

    @Test
    public void whenNoConfigIsSet_OutputsDefaultMessage2() {
        Assert.assertEquals("Hello, World!", ((OutputterConfiguration.CachingOutputter) outputter).getLastMessage());
    }

    @Test
    public void whenNoConfigIsSet_OutputsDefaultMessage3() {
        Assert.assertEquals("Hello, World!", ((OutputterConfiguration.CachingOutputter) outputter).getLastMessage());
    }

    @Configuration
    static class OutputterConfiguration {
        @Bean
        @Primary
        ExampleApplication.Outputter cachingOutput() {
            return new CachingOutputter();
        }

        static class CachingOutputter implements ExampleApplication.Outputter {
            private String lastMessage;

            @Override
            public void write(String message) {
                lastMessage = message;
            }

            public String getLastMessage() {
                return lastMessage;
            }
        }
    }
}
