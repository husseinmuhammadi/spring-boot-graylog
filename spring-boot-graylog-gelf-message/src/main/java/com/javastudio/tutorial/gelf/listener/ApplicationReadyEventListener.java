package com.javastudio.tutorial.gelf.listener;

import org.graylog2.gelfclient.*;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationReadyEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventListener.class);

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        
        GelfConfiguration gelfConfiguration = new GelfConfiguration("127.0.0.1", 12201)
                .transport(GelfTransports.UDP)
                .tcpKeepAlive(false)
                .queueSize(512)
                .connectTimeout(5000)
                .reconnectDelay(1000)
                .tcpNoDelay(true)
                .sendBufferSize(32768);
        GelfTransport gelfTransport = GelfTransports.create(gelfConfiguration);

        Arrays.stream(context.getBeanDefinitionNames())
                .map(beanName -> new GelfMessageBuilder(beanName)
                        .fullMessage(beanName)
                        .additionalField("facility", "test")
                        .level(GelfMessageLevel.INFO)
                        .build())
                .forEach(gelfMessage -> {
                    try {
                        gelfTransport.send(gelfMessage);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                });
    }
}
