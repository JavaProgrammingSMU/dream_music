package com.dreammusic.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(min = 2, max = 20, message = "Nickname must be between 2 and 20 characters")
    private String nickname;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String currentPassword;
}
