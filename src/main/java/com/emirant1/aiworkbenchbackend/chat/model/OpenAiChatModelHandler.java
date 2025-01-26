package com.emirant1.aiworkbenchbackend.chat.model;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class OpenAiChatModelHandler implements ChatModelHandler {
    private final OpenAiChatModel openAiChatModel;

    public OpenAiChatModelHandler(OpenAiChatModel openAiChatModel){
        this.openAiChatModel = openAiChatModel;
    }
    @Override
    public Flux<String> getChatResponseAsStream(String model, String input) {
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .model(model)
                .temperature(0.2)
                .build();

        Prompt prompt = new Prompt(input, openAiChatOptions);
        return openAiChatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getText());
    }
}