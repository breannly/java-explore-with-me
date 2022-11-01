package com.example.server.user.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    @Email
    @NotNull
    private String email;

    @NotBlank
    private String name;
}
