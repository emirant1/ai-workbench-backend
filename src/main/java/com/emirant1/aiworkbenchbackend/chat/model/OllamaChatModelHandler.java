package com.emirant1.aiworkbenchbackend.chat.model;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class OllamaChatModelHandler implements ChatModelHandler {

    private final OllamaChatModel chatModel;

    public OllamaChatModelHandler(OllamaChatModel chatModel){
        this.chatModel = chatModel;
    }

    @Override
    public Flux<String> getChatResponseAsStream(String model, String input) {
        UserMessage userMessage = new UserMessage(input);

        OllamaOptions chatOptions = OllamaOptions.builder()
                .model(model)
                .temperature(0.4)
                .build();

        Prompt prompt = new Prompt(userMessage, chatOptions);

        return chatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getContent());
    }
}