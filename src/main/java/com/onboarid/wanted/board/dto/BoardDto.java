package com.onboarid.wanted.board.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class BoardDto {
    @Getter
    public static class Post {
        @NotBlank(message = "제목은 공백일 수 없습니다.")
        private String title;
        @NotBlank(message = "내용은 공백일 수 없습니다.")
        private String content;
    }
    @Getter
    public static class Patch {

    }
    @Getter
    public static class Response {

    }
}
