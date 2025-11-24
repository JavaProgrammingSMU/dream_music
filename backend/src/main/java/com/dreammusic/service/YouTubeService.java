package com.dreammusic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String searchYouTubeVideoId(String title, String artist) {
        try {
            String searchQuery = URLEncoder.encode(artist + " " + title + " official", StandardCharsets.UTF_8);

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://www.googleapis.com")
                    .build();

            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/youtube/v3/search")
                            .queryParam("part", "snippet")
                            .queryParam("q", searchQuery)
                            .queryParam("type", "video")
                            .queryParam("maxResults", 1)
                            .queryParam("key", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode items = jsonNode.get("items");

            if (items != null && items.size() > 0) {
                return items.get(0).get("id").get("videoId").asText();
            }

            return null;
        } catch (Exception e) {
            log.error("Failed to search YouTube video", e);
            return null;
        }
    }
}
