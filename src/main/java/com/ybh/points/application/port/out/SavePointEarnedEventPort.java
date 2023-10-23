package com.ybh.points.application.port.out;

import com.ybh.points.domain.PointEvent;

public interface SavePointEarnedEventPort {
    Long save(PointEvent pointEvent);
}
