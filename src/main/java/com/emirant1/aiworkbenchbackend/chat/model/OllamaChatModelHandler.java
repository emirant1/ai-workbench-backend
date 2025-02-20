package com.emirant1.aiworkbenchbackend.chat.model;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class OllamaChatModelHandler implements ChatModelHandler {

    /* Other instance variables */
    private final ChatClient chatClient;

    public OllamaChatModelHandler(OllamaChatModel chatModel){
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    @Override
    public Flux<String> getChatResponseAsStream(String model, String input) {
        UserMessage userMessage = new UserMessage(input);

        OllamaOptions chatOptions = OllamaOptions.builder()
                .model(model)
                .temperature(0.4)
                .build();


        return chatClient.prompt(new Prompt(userMessage, chatOptions))
                .stream()
                .content();
    }
}