package com.emirant1.aiworkbenchbackend.chat.router;

import com.emirant1.aiworkbenchbackend.chat.handler.ChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ChatRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(ChatHandler chatHandler){
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/api/streaming/chat")
                                .and(RequestPredicates.accept(MediaType.TEXT_EVENT_STREAM)),
                        chatHandler::getChatResponseStream
                );
    }
}