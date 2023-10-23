package com.ybh.points.application;

import com.ybh.points.application.port.in.EarnPointCommand;
import com.ybh.points.application.port.in.EarnPointUseCase;
import com.ybh.points.application.port.out.SavePointEarnedEventPort;
import com.ybh.points.domain.PointEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EarnPointService implements EarnPointUseCase {
    private final SavePointEarnedEventPort savePointEarnedEventPort;

    @Autowired
    public EarnPointService(SavePointEarnedEventPort savePointEarnedEventPort) {
        this.savePointEarnedEventPort = savePointEarnedEventPort;
    }

    @Transactional
    @Override
    public Long earn(EarnPointCommand earnPointCommand) {
        PointEvent pointEarnedEvent = PointEvent.createPointEarnedEvent(
                earnPointCommand.getUserId(),
                earnPointCommand.getPointEventCause(),
                earnPointCommand.getPoint()
        );
        return savePointEarnedEventPort.save(pointEarnedEvent);
    }
}
