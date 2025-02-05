package com.emirant1.aiworkbenchbackend.chat.validator;

import com.emirant1.aiworkbenchbackend.chat.config.ChatProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class ChatModelValidator implements Validator {

    /* The validator properties */
    private static final String MODEL_PARAM = "model";

    /* Spring Beans */
    private final ChatProperties chatProperties;

    /* Other instance variables */
    private List<String> modelList;

    /**
     * This method checks if the provided object can be validated
     * @param clazz The class of the object that will be validated
     * @return true if the class of the object to validate is {@link String} as expected
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    /**
     * The validation business logic. The provided model must be one that is supported
     * by the software
     * @param target The model name, a {@link String}
     * @param errors The errors that will be updated if the model is not valid
     */
    @Override
    public void validate(Object target, Errors errors) {
        String model = (String)target;

        if(!modelList.contains(model)) {
            errors.reject(MODEL_PARAM, String.format("The model provided %s is not valid!", model));
        }
    }

    /**
     * This method will populate the list of models with the ones provided in the configuration file
     * see application.yaml.
     */
    @PostConstruct
    public void init(){
        log.info("Initializing ChatModelValidator...");
        modelList = chatProperties.getChatMappings().values().stream()
                .flatMap(
                        chatModelMappings -> chatModelMappings.getModels().stream()
                ).toList();

        log.info("{} elements will be used to validate the user input!", modelList.size());
    }
}