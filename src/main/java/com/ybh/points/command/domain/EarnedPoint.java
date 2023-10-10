package com.ybh.points.command.domain;

import com.ybh.common.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ybh.common.exception.ExceptionCode.POINT_EARN_VALUE_LESS_THAN_MINIMUM;
import static com.ybh.common.exception.ExceptionCode.MEMBER_ID_NULL;
import static com.ybh.points.command.domain.PointEventType.*;
import static java.time.LocalDateTime.*;

public class EarnedPoint {
    public static final int DEFAULT_EXPIRATION_PERIOD = 1;
    public static final int MINIMUM_EARNED_POINT = 1;

    private Long id;
    private final Long memberId;
    private final int earnedPoint;
    private final List<PointDetail> pointDetails;
    private final LocalDateTime earnedDateTime;
    private LocalDateTime expirationDateTime;

    private EarnedPoint(
            Long memberId,
            int earnedPoint,
            List<PointDetail> pointDetails,
            LocalDateTime earnedDateTime,
            LocalDateTime expirationDateTime
    ) {
        this.memberId = memberId;
        this.earnedPoint = earnedPoint;
        this.pointDetails = pointDetails;
        this.earnedDateTime = earnedDateTime;
        this.expirationDateTime = expirationDateTime;
    }

    public static EarnedPoint create(
            Long userId,
            int earnedPoint
    ) {
        validate(userId, earnedPoint);

        List<PointDetail> pointDetails = new ArrayList<>();
        pointDetails.add(PointDetail.of(EARNED, earnedPoint));

        return new EarnedPoint(
                userId,
                earnedPoint,
                pointDetails,
                now(),
                now().plusYears(DEFAULT_EXPIRATION_PERIOD)
        );
    }

    private static void validate(Long userId, int earnedPoint) {
        if (userId == null) {
            throw new BusinessException(MEMBER_ID_NULL);
        }

        if (earnedPoint < MINIMUM_EARNED_POINT) {
            throw new BusinessException(POINT_EARN_VALUE_LESS_THAN_MINIMUM, "적립 요청 포인트: " + earnedPoint);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarnedPoint that = (EarnedPoint) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
