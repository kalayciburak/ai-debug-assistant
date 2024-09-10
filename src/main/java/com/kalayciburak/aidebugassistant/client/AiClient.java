package com.kalayciburak.aidebugassistant.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiClient {
    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;
    @Value("${api.base-url}")
    private String baseUrl;
    @Value("${api.model}")
    private String apiModel;

    public AiClient() {
        this.restTemplate = new RestTemplate();
    }

    public String sendChatMessage(String messageContent) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var body = String.format(
                "{\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"model\": \"%s\"}",
                messageContent, apiModel
        );

        var request = new HttpEntity<>(body, headers);
        var response = restTemplate.postForEntity(
                baseUrl + "/v1/chat/completions",
                request,
                String.class
        );

        return response.getBody();
    }
}
