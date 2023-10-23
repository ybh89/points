package com.ybh.common.exception;

public enum ExceptionCode {
    MEMBER_ID_NULL(100000, "회원 아이디가 null 입니다."),
    POINT_EARN_VALUE_LESS_THAN_MINIMUM(100001, "포인트가 적립 최소값보다 작습니다."),
    POINT_EVENT_TYPE_NULL(100002, "포인트 이벤트 타입이 null 입니다."),
    POINT_EVENT_CAUSE_NULL(100003, "포인트 이벤트 발생 원인이 null 입니다."),
    NEW_POINT_EVENT_NOT_EXIST(100004, "새로운 포인트 이벤트가 존재하지 않습니다."),

    ;

    private int code;
    private String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
