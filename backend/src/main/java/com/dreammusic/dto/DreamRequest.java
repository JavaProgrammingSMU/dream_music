package com.dreammusic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DreamRequest {

    @NotBlank(message = "Dream content is required")
    private String dreamContent;
}
