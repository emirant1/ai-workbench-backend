package com.emirant1.aiworkbenchbackend.chat.factory;

import com.emirant1.aiworkbenchbackend.chat.config.ChatProperties;
import com.emirant1.aiworkbenchbackend.chat.model.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatModelHandlerFactory {
    /* Spring Beans */
    private final ApplicationContext applicationContext;
    private final ChatProperties chatProperties;

    /* Other instance variables */
    private Map<String, ChatModelHandler> beanMapping;

    public ChatModelHandler getChatModelHandler(String model){
        return beanMapping.get(model);
    }

    @PostConstruct
    public void init(){
        beanMapping = chatProperties.getChatMappings().values().stream()
                .flatMap(
                        mappings -> mappings.getModels().stream()
                                .map(model -> buildMapEntry(model, mappings.getHandlerClass()))
                )
                .collect(
                        toMap(Map.Entry::getKey, entry -> applicationContext.getBean(entry.getValue()))
                );
    }

    @SuppressWarnings("unchecked")
    private <T extends ChatModelHandler> Map.Entry<String, Class<T>> buildMapEntry(String model, String className){
        try {
            return new AbstractMap.SimpleImmutableEntry<>(model, (Class<T>)Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}