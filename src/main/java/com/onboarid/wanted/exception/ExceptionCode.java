package com.onboarid.wanted.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(404, "회원이 존재하지 않습니다."),
    UNAUTHORIZED(401, "권한이 없습니다.");

    @Getter
    public int statusCode;
    @Getter
    public String message;

    ExceptionCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
