package com.dreammusic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dream_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DreamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String dreamContent;

    @Column(columnDefinition = "TEXT")
    private String easternInterpretation;

    @Column(columnDefinition = "TEXT")
    private String westernInterpretation;

    @Column(columnDefinition = "TEXT")
    private String psychologicalAnalysis;

    @Column
    private String recommendedMusicTitle;

    @Column
    private String recommendedMusicArtist;

    @Column
    private String youtubeVideoId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
