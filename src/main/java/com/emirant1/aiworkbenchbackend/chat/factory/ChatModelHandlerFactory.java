package com.emirant1.aiworkbenchbackend.chat.factory;

import com.emirant1.aiworkbenchbackend.chat.model.ChatModelHandler;
import com.emirant1.aiworkbenchbackend.chat.model.OllamaChatModelHandler;
import com.emirant1.aiworkbenchbackend.chat.model.OpenAiChatModelHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChatModelHandlerFactory {
    /* Spring Beans */
    private final ApplicationContext applicationContext;

    /* Other instance variables */
    private Map<String, ChatModelHandler> beanMapping;

    public ChatModelHandlerFactory(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public ChatModelHandler getChatModelHandler(String model){
        return  beanMapping.get(model);
    }

    @PostConstruct
    public void init(){
        beanMapping = Map.of(
                "llama3.2", applicationContext.getBean(OllamaChatModelHandler.class),
                "mistral", applicationContext.getBean(OllamaChatModelHandler.class),
                "gpt-4o", applicationContext.getBean(OpenAiChatModelHandler.class)
        );
    }
}