package com.dreammusic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> interpretDream(String dreamContent) {
        String prompt = createDreamInterpretationPrompt(dreamContent);
        String response = callOpenAI(prompt);
        return parseDreamInterpretation(response);
    }

    public Map<String, String> recommendMusic(String dreamContent) {
        String prompt = createMusicRecommendationPrompt(dreamContent);
        String response = callOpenAI(prompt);
        return parseMusicRecommendation(response);
    }

    private String createDreamInterpretationPrompt(String dreamContent) {
        return """
            당신은 꿈 해몽 전문가입니다. 사용자가 꾼 꿈을 분석하여 다음 세 가지 관점에서 해석해 주세요.

            사용자의 꿈 내용: "%s"

            다음 형식으로 정확히 응답해 주세요 (JSON 형식):
            {
                "eastern": "동양적 관점의 해몽 (한국, 중국, 일본의 전통적인 꿈 해석 방식을 바탕으로. 길몽/흉몽 여부, 상징적 의미, 조상님의 메시지 등을 포함하여 2-3문단으로 상세히 설명)",
                "western": "서양적 관점의 해몽 (프로이트, 융의 정신분석학적 관점을 바탕으로. 무의식의 욕구, 상징의 의미, 자아와의 관계 등을 포함하여 2-3문단으로 상세히 설명)",
                "psychological": "현재 무의식적 심리 상태 분석 (꿈을 통해 드러나는 현재 심리 상태, 스트레스 요인, 내면의 갈등이나 욕구, 그리고 도움이 될 수 있는 조언을 2-3문단으로 상세히 설명)"
            }

            반드시 JSON 형식으로만 응답하고, 다른 텍스트는 포함하지 마세요.
            """.formatted(dreamContent);
    }

    private String createMusicRecommendationPrompt(String dreamContent) {
        return """
            당신은 음악 추천 전문가입니다. 사용자가 꾼 꿈의 분위기와 감정을 분석하여 어울리는 음악을 추천해 주세요.

            사용자의 꿈 내용: "%s"

            꿈의 분위기, 감정, 주제를 고려하여 가장 잘 어울리는 음악 1곡을 추천해 주세요.
            유명하고 YouTube에서 쉽게 찾을 수 있는 곡으로 추천해 주세요.

            다음 형식으로 정확히 응답해 주세요 (JSON 형식):
            {
                "title": "노래 제목",
                "artist": "아티스트명",
                "reason": "이 곡을 추천하는 이유 (꿈의 분위기와 어떻게 연결되는지 1-2문장으로 설명)"
            }

            반드시 JSON 형식으로만 응답하고, 다른 텍스트는 포함하지 마세요.
            """.formatted(dreamContent);
    }

    private String callOpenAI(String prompt) {
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(message));
        requestBody.put("temperature", 0.7);

        try {
            String response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("choices").get(0).get("message").get("content").asText();
        } catch (Exception e) {
            log.error("OpenAI API call failed", e);
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage());
        }
    }

    private Map<String, String> parseDreamInterpretation(String response) {
        Map<String, String> result = new HashMap<>();
        try {
            String cleanedResponse = response.trim();
            if (cleanedResponse.startsWith("```json")) {
                cleanedResponse = cleanedResponse.substring(7);
            }
            if (cleanedResponse.startsWith("```")) {
                cleanedResponse = cleanedResponse.substring(3);
            }
            if (cleanedResponse.endsWith("```")) {
                cleanedResponse = cleanedResponse.substring(0, cleanedResponse.length() - 3);
            }
            cleanedResponse = cleanedResponse.trim();

            JsonNode jsonNode = objectMapper.readTree(cleanedResponse);
            result.put("eastern", jsonNode.get("eastern").asText());
            result.put("western", jsonNode.get("western").asText());
            result.put("psychological", jsonNode.get("psychological").asText());
        } catch (Exception e) {
            log.error("Failed to parse dream interpretation", e);
            result.put("eastern", "해석을 불러오는 데 실패했습니다.");
            result.put("western", "해석을 불러오는 데 실패했습니다.");
            result.put("psychological", "해석을 불러오는 데 실패했습니다.");
        }
        return result;
    }

    private Map<String, String> parseMusicRecommendation(String response) {
        Map<String, String> result = new HashMap<>();
        try {
            String cleanedResponse = response.trim();
            if (cleanedResponse.startsWith("```json")) {
                cleanedResponse = cleanedResponse.substring(7);
            }
            if (cleanedResponse.startsWith("```")) {
                cleanedResponse = cleanedResponse.substring(3);
            }
            if (cleanedResponse.endsWith("```")) {
                cleanedResponse = cleanedResponse.substring(0, cleanedResponse.length() - 3);
            }
            cleanedResponse = cleanedResponse.trim();

            JsonNode jsonNode = objectMapper.readTree(cleanedResponse);
            result.put("title", jsonNode.get("title").asText());
            result.put("artist", jsonNode.get("artist").asText());
            result.put("reason", jsonNode.has("reason") ? jsonNode.get("reason").asText() : "");
        } catch (Exception e) {
            log.error("Failed to parse music recommendation", e);
            result.put("title", "Unknown");
            result.put("artist", "Unknown");
            result.put("reason", "");
        }
        return result;
    }
}
