package com.ybh.points.domain;

import com.ybh.common.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ybh.common.exception.ExceptionCode.*;
import static com.ybh.points.domain.PointEventType.EARNED;
import static java.time.LocalDateTime.now;

/**
 * 포인트 이벤트 (적립, 차감, 만료)
 * 불변객체, 절대 수정되면 안됨.
 */
public final class PointEvent {
    public static final int DEFAULT_EXPIRATION_PERIOD = 1;
    public static final int MINIMUM_EARNED_POINT = 1;

    private Long id;
    private final Long memberId;
    private final PointEventCause pointEventCause;
    private final PointEventType pointEventType;
    private final int pointAmount;
    private final List<PointEventDetail> pointEventDetails;
    private final LocalDateTime createdDateTime;
    private LocalDateTime expirationDateTime;

    private PointEvent(
            Long memberId,
            PointEventCause pointEventCause,
            PointEventType pointEventType,
            int pointAmount,
            List<PointEventDetail> pointEventDetails,
            LocalDateTime createdDateTime,
            LocalDateTime expirationDateTime
    ) {
        this.memberId = memberId;
        this.pointEventCause = pointEventCause;
        this.pointEventType = pointEventType;
        this.pointAmount = pointAmount;
        this.pointEventDetails = pointEventDetails;
        this.createdDateTime = createdDateTime;
        this.expirationDateTime = expirationDateTime;
    }

    public static PointEvent createPointEarnedEvent(
            Long memberId,
            PointEventCause pointEventCause,
            int pointAmount
    ) {
        if (memberId == null) {
            throw new BusinessException(MEMBER_ID_NULL);
        }

        if (pointAmount < MINIMUM_EARNED_POINT) {
            throw new BusinessException(POINT_EARN_VALUE_LESS_THAN_MINIMUM, "적립 요청 포인트: " + pointAmount);
        }

        if (pointEventCause == null) {
            throw new BusinessException(POINT_EVENT_CAUSE_NULL);
        }

        return new PointEvent(
                memberId,
                pointEventCause,
                EARNED,
                pointAmount,
                List.of(PointEventDetail.of(pointAmount)),
                now(),
                now().plusYears(DEFAULT_EXPIRATION_PERIOD)
        );
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public PointEventCause getPointEventCause() {
        return pointEventCause;
    }

    public PointEventType getPointEventType() {
        return pointEventType;
    }

    public List<PointEventDetail> getPointEventDetails() {
        return new ArrayList<>(pointEventDetails);
    }

    public List<PointEventDetail> getPointEvents() {
        return new ArrayList<>(pointEventDetails);
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointEvent that = (PointEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
