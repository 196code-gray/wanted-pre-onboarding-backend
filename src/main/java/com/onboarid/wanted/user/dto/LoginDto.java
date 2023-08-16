package com.onboarid.wanted.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
public class LoginDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @Size(min = 8, message = "길이가 8글자 이상이여야합니다.")
    @NotBlank
    private String password;
}
