package com.dreammusic.controller;

import com.dreammusic.dto.DreamRequest;
import com.dreammusic.dto.DreamResponse;
import com.dreammusic.service.DreamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dreams")
@RequiredArgsConstructor
public class DreamController {

    private final DreamService dreamService;

    @PostMapping("/interpret")
    public ResponseEntity<DreamResponse> interpretDream(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DreamRequest request) {
        return ResponseEntity.ok(dreamService.interpretDream(userDetails.getUsername(), request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<DreamResponse>> getDreamHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(dreamService.getDreamHistory(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DreamResponse> getDreamById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        return ResponseEntity.ok(dreamService.getDreamById(userDetails.getUsername(), id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDream(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        dreamService.deleteDream(userDetails.getUsername(), id);
        return ResponseEntity.ok().body(Map.of("message", "Dream record deleted successfully"));
    }
}
