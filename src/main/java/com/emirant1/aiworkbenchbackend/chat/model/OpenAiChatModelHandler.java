package com.emirant1.aiworkbenchbackend.chat.model;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class OpenAiChatModelHandler implements ChatModelHandler {
    /* Other instance variables */
    private final ChatClient chatClient;

    public OpenAiChatModelHandler(OpenAiChatModel openAiChatModel){
        this.chatClient = ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    @Override
    public Flux<String> getChatResponseAsStream(String model, String input) {
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .model(model)
                .temperature(0.2)
                .build();

        return chatClient.prompt(new Prompt(input, openAiChatOptions))
                .stream()
                .content();
    }
}