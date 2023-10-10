package com.ybh.points.command.domain;

import com.ybh.common.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.util.List;

import static com.ybh.common.exception.ExceptionCode.MEMBER_ID_NULL;
import static com.ybh.common.exception.ExceptionCode.POINT_EARN_VALUE_LESS_THAN_MINIMUM;

@DisplayName("EarnedPoint 단위테스트")
class EarnedPointTest {
    @Test
    void 생성시_회원_아이디가_null이면_에러() {
        Assertions.assertThatThrownBy(() -> EarnedPoint.create(null, 100))
                .isInstanceOf(BusinessException.class)
                .hasMessage(MEMBER_ID_NULL.message());
    }

    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    @ParameterizedTest
    void 생성시_적립포인트가_0이하이면_에러(int value) {
        Assertions.assertThatThrownBy(() -> EarnedPoint.create(1L, value))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(POINT_EARN_VALUE_LESS_THAN_MINIMUM.message());
    }

    @Test
    void 생성시_입력데이터에_문제가없으면_정상_생성됨() {
        Assertions.assertThatCode(() -> EarnedPoint.create(1L, 100))
                .doesNotThrowAnyException();
    }

    @Test
    void 생성시_정상생성됐다면_포인트상세에_적립이벤트가_존재해야한다() throws NoSuchFieldException, IllegalAccessException {
        // given
        EarnedPoint earnedPoint = EarnedPoint.create(1L, 100);
        Field field = earnedPoint.getClass().getDeclaredField("pointDetails");
        field.setAccessible(true);
        List<PointDetail> pointDetails = (List<PointDetail>) field.get(earnedPoint);

        // when
        // then
        Assertions.assertThat(pointDetails).containsExactly(PointDetail.of(PointEventType.EARNED, 100));
    }
}