package com.ybh.points.adapter.out.persistence;

import com.ybh.points.domain.PointEvent;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "point_event")
@Entity
class PointEventJpaEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @Column(name = "cause", nullable = false, updatable = false)
    private String cause;

    @Column(name = "event", nullable = false, updatable = false)
    private String event;

    @Column(name = "point", nullable = false, updatable = false)
    private int point;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(cascade = PERSIST, mappedBy = "pointEventJpaEntity")
    private List<PointEventDetailJpaEntity> pointEventDetailJpaEntities = new ArrayList<>();

    @Deprecated
    protected PointEventJpaEntity() {
    }

    private PointEventJpaEntity(
            Long memberId,
            String cause,
            String event,
            int point,
            LocalDateTime createdAt,
            List<PointEventDetailJpaEntity> pointEventDetailJpaEntities
    ) {
        this.memberId = memberId;
        this.cause = cause;
        this.event = event;
        this.point = point;
        this.createdAt = createdAt;
        this.pointEventDetailJpaEntities = pointEventDetailJpaEntities;
    }

    public static PointEventJpaEntity from(PointEvent pointEvent) {
        return new PointEventJpaEntity(
                pointEvent.getMemberId(),
                pointEvent.getPointEventCause().name(),
                pointEvent.getPointEventType().name(),
                pointEvent.getPointAmount(),
                pointEvent.getCreatedDateTime(),
                null
        );
    }

    public void setPointEventDetailJpaEntities(List<PointEventDetailJpaEntity> pointEventDetailJpaEntities) {
        this.pointEventDetailJpaEntities = pointEventDetailJpaEntities;
        for (PointEventDetailJpaEntity pointEventDetailJpaEntity : pointEventDetailJpaEntities) {
            pointEventDetailJpaEntity.setPointEventJpaEntity(this);
        }
    }
}
