package com.ybh.points.adapter.out.persistence;

import com.ybh.points.application.port.out.SavePointEarnedEventPort;
import com.ybh.points.domain.PointEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ybh.points.domain.PointEventType.EARNED;

@Component
class PointEarnedEventPersistenceAdapter implements SavePointEarnedEventPort {
    private final PointEventJpaRepository pointEventJpaRepository;
    private final PointEventDetailJpaRepository pointEventDetailJpaRepository;

    @Autowired
    PointEarnedEventPersistenceAdapter(
            PointEventJpaRepository pointEventJpaRepository,
            PointEventDetailJpaRepository pointEventDetailJpaRepository
    ) {
        this.pointEventJpaRepository = pointEventJpaRepository;
        this.pointEventDetailJpaRepository = pointEventDetailJpaRepository;
    }

    @Override
    public Long save(PointEvent pointEvent) {
        if (pointEvent.getPointEventType() != EARNED) {
            throw new IllegalArgumentException("적립 이벤트만 저장할 수 있습니다.");
        }

        PointEventJpaEntity pointEventJpaEntity = PointEventJpaEntity.from(pointEvent);
        List<PointEventDetailJpaEntity> pointEventDetailJpaEntities = pointEvent.getPointEventDetails().stream()
                .map(pointEventDetail -> PointEventDetailJpaEntity.of(pointEventJpaEntity, pointEventDetail.getPoint()))
                .toList();
        pointEventJpaEntity.setPointEventDetailJpaEntities(pointEventDetailJpaEntities);

        pointEventJpaRepository.save(pointEventJpaEntity);

        for (PointEventDetailJpaEntity pointEventDetailJpaEntity : pointEventDetailJpaEntities) {
            pointEventDetailJpaEntity.setDetailEarnedId(pointEventDetailJpaEntity.getId());
        }

        return pointEventJpaEntity.getId();
    }
}
