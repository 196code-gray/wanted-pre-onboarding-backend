package com.onboarid.wanted.user.controller;

import com.onboarid.wanted.user.mapper.UserMapper;
import com.onboarid.wanted.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity postUser () {
        return null;
    }

    @PatchMapping
    public ResponseEntity patchUser () {
        return null;
    }

}
