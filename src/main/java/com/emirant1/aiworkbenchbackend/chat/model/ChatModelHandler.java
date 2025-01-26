package com.emirant1.aiworkbenchbackend.chat.model;

import reactor.core.publisher.Flux;

public interface ChatModelHandler {
    Flux<String> getChatResponseAsStream(String model, String input);
}