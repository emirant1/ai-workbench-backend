package com.emirant1.aiworkbenchbackend.chat.handler;

import com.emirant1.aiworkbenchbackend.chat.factory.ChatModelHandlerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ChatHandler {

    /* Spring Beans */
    private final ChatModelHandlerFactory chatModelHandlerFactory;

    private ChatHandler(ChatModelHandlerFactory chatModelHandlerFactory){
        this.chatModelHandlerFactory = chatModelHandlerFactory;
    }

    public Mono<ServerResponse> getChatResponseStream(ServerRequest request) {
        String model = request.queryParam("model").orElse("");
        String input = request.queryParam("input").orElse("");

        Flux<String> chatResponseAsStream = chatModelHandlerFactory.getChatModelHandler(model)
                .getChatResponseAsStream(model, input);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(chatResponseAsStream, String.class);
    }
}