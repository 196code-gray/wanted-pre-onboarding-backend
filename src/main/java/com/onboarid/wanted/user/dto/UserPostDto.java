package com.onboarid.wanted.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Builder
public class UserPostDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Size(min = 8, message = "길이가 8글자 이상이여야합니다.")
    @NotBlank
    private String password;
}
