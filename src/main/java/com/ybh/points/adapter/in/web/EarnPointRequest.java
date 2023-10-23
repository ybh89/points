package com.ybh.points.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class EarnPointRequest {
    @NotNull
    private final Long userId;
    @Positive
    private final int point;
    @NotBlank
    private final String cause;

    public EarnPointRequest(Long userId, int point, String cause) {
        this.userId = userId;
        this.point = point;
        this.cause = cause;
    }
}
