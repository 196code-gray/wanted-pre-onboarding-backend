package com.onboarid.wanted.board.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
        @Positive
        private long boardId;
        private String title;
        private String content;
    }
    @Getter @Builder
    public static class Response {
        private String title;
        private String content;
    }
}
