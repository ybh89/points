package com.ybh.points.application;

import com.ybh.points.application.port.in.PointEarnCommand;
import com.ybh.points.application.port.in.PointEarnUseCase;
import com.ybh.points.application.port.out.SavePointEarnedEventPort;
import com.ybh.points.domain.PointEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointEarnService implements PointEarnUseCase {
    private final SavePointEarnedEventPort savePointEarnedEventPort;

    @Autowired
    public PointEarnService(SavePointEarnedEventPort savePointEarnedEventPort) {
        this.savePointEarnedEventPort = savePointEarnedEventPort;
    }

    @Transactional
    @Override
    public Long earn(PointEarnCommand pointEarnCommand) {
        PointEvent pointEarnedEvent = PointEvent.createPointEarnedEvent(
                pointEarnCommand.getUserId(),
                pointEarnCommand.getPointEventCause(),
                pointEarnCommand.getPoint()
        );
        return savePointEarnedEventPort.save(pointEarnedEvent);
    }
}
