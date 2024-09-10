package com.kalayciburak.aidebugassistant.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalayciburak.aidebugassistant.client.AiClient;
import com.kalayciburak.aidebugassistant.exception.AiEnhancedException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.kalayciburak.aidebugassistant.util.constant.Language.LANGUAGE_MAP;
import static com.kalayciburak.aidebugassistant.util.constant.Message.PROMPT;

@Aspect
@Component
public class ErrorHandlingAspect {
    private final AiClient aiClient;
    private final ObjectMapper mapper;

    @Value("${api.response.language}")
    private String language;

    public ErrorHandlingAspect(AiClient aiClient, ObjectMapper mapper) {
        this.aiClient = aiClient;
        this.mapper = mapper;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void handleException(Exception ex) throws Exception {
        normalizeLanguage();
        var prompt = String.format(PROMPT, ex.getMessage(), language, language);
        var json = aiClient.sendChatMessage(prompt);

        var rootNode = mapper.readTree(json);
        var contentNode = rootNode.path("choices").get(0).path("message").path("content");
        var aiMessage = contentNode.asText();

        throwDynamicException(ex, aiMessage);
    }

    private void throwDynamicException(Exception originalException, String aiMessage) throws Exception {
        var exceptionClass = originalException.getClass();
        try {
            var constructor = exceptionClass.getConstructor(String.class);
            throw constructor.newInstance(aiMessage);
        } catch (NoSuchMethodException e) {
            throw new AiEnhancedException(aiMessage);
        }
    }

    private void normalizeLanguage() {
        language = LANGUAGE_MAP.getOrDefault(language.toLowerCase(), language);
    }
}
