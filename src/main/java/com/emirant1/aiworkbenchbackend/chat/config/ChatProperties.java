package com.emirant1.aiworkbenchbackend.chat.config;

import com.emirant1.aiworkbenchbackend.chat.model.ChatModelMappings;
import com.emirant1.aiworkbenchbackend.chat.model.ChatModelProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "workbench.mappings")
@Getter
@Setter
public class ChatProperties {
    private Map<ChatModelProvider, ChatModelMappings> chatMappings;
}