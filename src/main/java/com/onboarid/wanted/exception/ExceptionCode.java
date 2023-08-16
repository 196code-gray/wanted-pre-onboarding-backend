package com.onboarid.wanted.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(404, "회원이 존재하지 않습니다."),
    USER_EXITS(402, "유저가 이미 존재합니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    UNSUPPORTED_MEDIA_TYPE(402, "허용되지 않는 데이터 타입입니다."),
    METHOD_NOT_ALLOWED(402, "허용되지 않는 요청입니다.");

    @Getter
    public int statusCode;
    @Getter
    public String message;

    ExceptionCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
