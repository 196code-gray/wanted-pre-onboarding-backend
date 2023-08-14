package com.onboarid.wanted.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
public class LoginDto {
    @Email
    private String email;
    @Size(min = 8)
    private String password;
}
