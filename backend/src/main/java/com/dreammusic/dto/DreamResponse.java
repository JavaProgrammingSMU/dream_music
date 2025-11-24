package com.dreammusic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DreamResponse {
    private Long id;
    private String dreamContent;
    private String easternInterpretation;
    private String westernInterpretation;
    private String psychologicalAnalysis;
    private String recommendedMusicTitle;
    private String recommendedMusicArtist;
    private String youtubeVideoId;
    private LocalDateTime createdAt;
}
