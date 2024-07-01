package com.example.devicespriceclassifier.service;

import com.example.devicespriceclassifier.model.Device;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Service
public class PredictionService {

    private final RestTemplate restTemplate;

    @Value("${prediction.api.url}")
    private String predictionApiUrl;

    public PredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String predictPrice(Device device) {
        HttpEntity<Device> request = new HttpEntity<>(device);
        ResponseEntity<String> response = restTemplate.postForEntity(predictionApiUrl, request, String.class);
        return response.getBody();
    }
}
