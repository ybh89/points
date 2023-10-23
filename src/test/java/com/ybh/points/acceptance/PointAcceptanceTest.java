package com.ybh.points.acceptance;

import com.ybh.points.adapter.in.web.EarnPointRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.CREATED;

@DisplayName("point 인수 테스트")
class PointAcceptanceTest extends AcceptanceTest {
    static final String BASE_URL = "/points";

    /**
     * 1. 포인트 적립
     * 2. 전체 포인트 조회
     * 2. 포인트 전체 차감
     * 3. 전체 포인트 조회
     */
    @Test
    void 포인트_전체차감_시나리오() {
        EarnPointRequest earnPointRequest = new EarnPointRequest(1L, 1000, "WROTE_REVIEW");
        ExtractableResponse<Response> earnResponse = 포인트_적립_요청(earnPointRequest);
        포인트_적립됨(earnResponse);
    }

    /**
     * 1. 포인트 적립
     * 2. 포인트 부분 차감
     * 3. 전체 포인트 조회
     */
    @Test
    void 포인트_부분차감_시나리오() {
        EarnPointRequest earnPointRequest = new EarnPointRequest(1L, 1000, "WROTE_REVIEW");
        ExtractableResponse<Response> earnResponse = 포인트_적립_요청(earnPointRequest);
        포인트_적립됨(earnResponse);
    }

    /**
     * 1. 포인트 적립
     * 2. 포인트 부분 차감
     * 3. 포인트 적립
     * 4. 전체 포인트 조회
     * 5. 포인트 부분 차감 취소
     * 6. 전체 포인트 조회
     */
    @Test
    void 포인트_부분차감취소_시나리오() {
        EarnPointRequest earnPointRequest = new EarnPointRequest(1L, 1000, "WROTE_REVIEW");
        ExtractableResponse<Response> earnResponse = 포인트_적립_요청(earnPointRequest);
        포인트_적립됨(earnResponse);
    }

    private void 포인트_적립됨(ExtractableResponse<Response> earnResponse) {
        assertHttpStatus(earnResponse, CREATED);
    }

    private ExtractableResponse<Response> 포인트_적립_요청(EarnPointRequest earnPointRequest) {
        return post(BASE_URL, earnPointRequest);
    }
}
