package com.onboarid.wanted.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Builder
public class UserPostDto {
    @Email
    private String email;
//    @Pattern(regexp = "/^{8,}$/", message = "비밀번호는 최소 8글자 이상")
    @Size(min = 8) @NotBlank
    private String password;
}
