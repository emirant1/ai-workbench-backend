package com.emirant1.aiworkbenchbackend.chat.handler;

import com.emirant1.aiworkbenchbackend.chat.factory.ChatModelHandlerFactory;
import com.emirant1.aiworkbenchbackend.chat.validator.ChatModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChatHandler {

    /* Spring Beans */
    private final ChatModelHandlerFactory chatModelHandlerFactory;
    private final ChatModelValidator chatModelValidator;

    public Mono<ServerResponse> getChatResponseStream(ServerRequest request) {
        /* Request parameter retrieval */
        String model = request.queryParam("model").orElse("");
        String input = request.queryParam("input").orElse("");

        /* User input validation */
        BindingResult bindingResult = new BeanPropertyBindingResult(model, String.class.getName());
        chatModelValidator.validate(model, bindingResult);

        if(bindingResult.hasErrors()){
            return ServerResponse.badRequest().bodyValue(bindingResult.getAllErrors());
        }

        /* Fetch the response from a model*/
        Flux<String> chatResponseAsStream = chatModelHandlerFactory.getChatModelHandler(model)
                .getChatResponseAsStream(model, input);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(chatResponseAsStream, String.class);
    }
}