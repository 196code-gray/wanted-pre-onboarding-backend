package com.onboarid.wanted.user.controller;

import com.onboarid.wanted.user.dto.UserPostDto;
import com.onboarid.wanted.user.entity.User;
import com.onboarid.wanted.user.mapper.UserMapper;
import com.onboarid.wanted.user.service.UserService;
import com.onboarid.wanted.util.URICreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/sign-up")
    public ResponseEntity postUser (@RequestBody @Valid UserPostDto postDto) {
        User user = userService.savedUser(userMapper.PostDtoToEntity(postDto));
        URI uri = URICreator.createUri("/users/sign-up", user.getUserId());

        return ResponseEntity.created(uri).build();
    }
}
