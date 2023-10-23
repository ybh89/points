package com.ybh.points.domain;

import java.util.Objects;

/**
 * 포인트 차감시, 여러 적립 포인트에서 차감될 수 있기 때문에 상세 엔티티가 필요.
 * 즉, 하나의 차감 이벤트는 여러 상세 이벤트로 매핑될 수 있다. (일대다)
 * 적립 이벤트시에는 항상 상세 이벤트가 하나여야 한다. (일대일)
 * 만료 이벤트시에도 항상 상세 이벤트는 하나여야 한다. (일대일)
 * 불변객체, 절대 수정되면 안됨.
 */
public final class PointEventDetail {
    private Long id;
    private Long earnedPointEventDetailId;
    private final int point;

    private PointEventDetail(int point) {
        this.point = point;
    }

    public static PointEventDetail of(int amount) {
        return new PointEventDetail(amount);
    }

    public int getPoint() {
        return point;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointEventDetail that = (PointEventDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
