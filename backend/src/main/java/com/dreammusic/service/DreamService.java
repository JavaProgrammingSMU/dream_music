package com.dreammusic.service;

import com.dreammusic.dto.DreamRequest;
import com.dreammusic.dto.DreamResponse;
import com.dreammusic.entity.DreamRecord;
import com.dreammusic.entity.User;
import com.dreammusic.repository.DreamRecordRepository;
import com.dreammusic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DreamService {

    private final DreamRecordRepository dreamRecordRepository;
    private final UserRepository userRepository;
    private final OpenAIService openAIService;
    private final YouTubeService youTubeService;

    @Transactional
    public DreamResponse interpretDream(String email, DreamRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, String> interpretation = openAIService.interpretDream(request.getDreamContent());

        Map<String, String> musicRecommendation = openAIService.recommendMusic(request.getDreamContent());

        String youtubeVideoId = youTubeService.searchYouTubeVideoId(
                musicRecommendation.get("title"),
                musicRecommendation.get("artist")
        );

        DreamRecord dreamRecord = DreamRecord.builder()
                .user(user)
                .dreamContent(request.getDreamContent())
                .easternInterpretation(interpretation.get("eastern"))
                .westernInterpretation(interpretation.get("western"))
                .psychologicalAnalysis(interpretation.get("psychological"))
                .recommendedMusicTitle(musicRecommendation.get("title"))
                .recommendedMusicArtist(musicRecommendation.get("artist"))
                .youtubeVideoId(youtubeVideoId)
                .build();

        dreamRecordRepository.save(dreamRecord);

        return convertToResponse(dreamRecord);
    }

    public List<DreamResponse> getDreamHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return dreamRecordRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public DreamResponse getDreamById(String email, Long dreamId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DreamRecord dreamRecord = dreamRecordRepository.findById(dreamId)
                .orElseThrow(() -> new RuntimeException("Dream record not found"));

        if (!dreamRecord.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to dream record");
        }

        return convertToResponse(dreamRecord);
    }

    @Transactional
    public void deleteDream(String email, Long dreamId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DreamRecord dreamRecord = dreamRecordRepository.findById(dreamId)
                .orElseThrow(() -> new RuntimeException("Dream record not found"));

        if (!dreamRecord.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to dream record");
        }

        dreamRecordRepository.delete(dreamRecord);
    }

    private DreamResponse convertToResponse(DreamRecord dreamRecord) {
        return DreamResponse.builder()
                .id(dreamRecord.getId())
                .dreamContent(dreamRecord.getDreamContent())
                .easternInterpretation(dreamRecord.getEasternInterpretation())
                .westernInterpretation(dreamRecord.getWesternInterpretation())
                .psychologicalAnalysis(dreamRecord.getPsychologicalAnalysis())
                .recommendedMusicTitle(dreamRecord.getRecommendedMusicTitle())
                .recommendedMusicArtist(dreamRecord.getRecommendedMusicArtist())
                .youtubeVideoId(dreamRecord.getYoutubeVideoId())
                .createdAt(dreamRecord.getCreatedAt())
                .build();
    }
}
