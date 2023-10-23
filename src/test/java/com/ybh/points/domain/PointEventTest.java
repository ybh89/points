package com.ybh.points.domain;

import com.ybh.common.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.util.List;

import static com.ybh.common.exception.ExceptionCode.*;
import static com.ybh.points.domain.PointEventCause.*;

@DisplayName("EarnedPoint 단위테스트")
class PointEventTest {
    @Test
    void 적립이벤트_생성시_회원_아이디가_null이면_에러() {
        Assertions.assertThatThrownBy(() -> PointEvent.createPointEarnedEvent(null, WROTE_REVIEW, 100))
                .isInstanceOf(BusinessException.class)
                .hasMessage(MEMBER_ID_NULL.message());
    }

    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    @ParameterizedTest
    void 적립이벤트_생성시_적립포인트가_0이하이면_에러(int value) {
        Assertions.assertThatThrownBy(() -> PointEvent.createPointEarnedEvent(1L, WROTE_REVIEW, value))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(POINT_EARN_VALUE_LESS_THAN_MINIMUM.message());
    }

    @Test
    void 적립이벤트_생성시_이벤트사유가_null이면_에러() {
        Assertions.assertThatThrownBy(() -> PointEvent.createPointEarnedEvent(1L, null, 100))
                .isInstanceOf(BusinessException.class)
                .hasMessage(POINT_EVENT_CAUSE_NULL.message());
    }

    @Test
    void 적립이벤트_생성시_입력데이터에_문제가없으면_정상_생성됨() {
        Assertions.assertThatCode(() -> PointEvent.createPointEarnedEvent(1L, WROTE_REVIEW,  100))
                .doesNotThrowAnyException();
    }

    @Test
    void 적립이벤트_생성시_정상생성됐다면_포인트이벤트상세가_하나_존재해야한다() throws NoSuchFieldException, IllegalAccessException {
        // given
        PointEvent pointEvent = PointEvent.createPointEarnedEvent(1L, WROTE_REVIEW,  100);
        Field field = pointEvent.getClass().getDeclaredField("pointEventDetails");
        field.setAccessible(true);
        List<PointEventDetail> pointEventDetails = (List<PointEventDetail>) field.get(pointEvent);

        // when
        // then
        Assertions.assertThat(pointEventDetails.size()).isEqualTo(1);
    }
}