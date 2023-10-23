package com.ybh.points.application.port.in;

import com.ybh.points.domain.PointEventCause;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class EarnPointCommand extends SelfValidating<EarnPointCommand> {
    @NotNull
    private final Long userId;
    @Positive
    private final int point;
    @NotNull
    private final PointEventCause pointEventCause;

    public EarnPointCommand(Long userId, int point, String cause) {
        this.userId = userId;
        this.point = point;
        this.pointEventCause = PointEventCause.valueOf(cause);
        this.validateSelf();
    }
}
