package com.ybh.points.command.domain;

import com.ybh.common.exception.BusinessException;

import java.util.Objects;

import static com.ybh.common.exception.ExceptionCode.POINT_EVENT_TYPE_NULL;

public class PointDetail {
    private final PointEventType pointEventType;
    private final int amount;

    private PointDetail(PointEventType pointEventType, int amount) {
        this.pointEventType = pointEventType;
        this.amount = amount;
    }

    public static PointDetail of(PointEventType pointEventType, int amount) {
        if (pointEventType == null) {
            throw new BusinessException(POINT_EVENT_TYPE_NULL);
        }

        return new PointDetail(pointEventType, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointDetail that = (PointDetail) o;
        return amount == that.amount && pointEventType == that.pointEventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointEventType, amount);
    }
}
