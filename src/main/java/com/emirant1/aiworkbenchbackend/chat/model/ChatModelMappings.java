package com.emirant1.aiworkbenchbackend.chat.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatModelMappings {
    private List<String> models;
    private String handlerClass;
}