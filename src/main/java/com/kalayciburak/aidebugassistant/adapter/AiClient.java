package com.kalayciburak.aidebugassistant.adapter;

import com.kalayciburak.aidebugassistant.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiClient implements ChatService {
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

    @Override
    public String sendMessage(String messageContent) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var requestBodyFormat = "{\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"model\": \"%s\"}";
        var body = String.format(requestBodyFormat, messageContent, apiModel);

        var request = new HttpEntity<>(body, headers);
        var url = baseUrl + "/v1/chat/completions";

        return restTemplate.postForObject(url, request, String.class);
    }
}
