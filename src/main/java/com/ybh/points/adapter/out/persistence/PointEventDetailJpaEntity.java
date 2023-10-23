package com.ybh.points.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "point_event_detail")
@Entity
class PointEventDetailJpaEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "point_event_id", nullable = false, updatable = false)
    @ManyToOne
    private PointEventJpaEntity pointEventJpaEntity;

    @Column(name = "detail_earned_id")
    private Long detailEarnedId;

    @Column(name = "point", nullable = false, updatable = false)
    private int point;

    @Deprecated
    protected PointEventDetailJpaEntity() {
    }

    public PointEventDetailJpaEntity(
            PointEventJpaEntity pointEventJpaEntity,
            Long detailEarnedId,
            int point
    ) {
        this.pointEventJpaEntity = pointEventJpaEntity;
        this.detailEarnedId = detailEarnedId;
        this.point = point;
    }

    public static PointEventDetailJpaEntity of(PointEventJpaEntity pointEventJpaEntity, int point) {
        return new PointEventDetailJpaEntity(pointEventJpaEntity, null, point);
    }
}
