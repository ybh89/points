package com.ybh.points.adapter.in.web;

import com.ybh.points.application.port.in.PointEarnCommand;
import com.ybh.points.application.port.in.PointEarnUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/points")
@RestController
class PointEarnController {
    private final PointEarnUseCase pointEarnUseCase;

    @Autowired
    PointEarnController(PointEarnUseCase pointEarnUseCase) {
        this.pointEarnUseCase = pointEarnUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> earnPoint(@RequestBody @Valid PointEarnRequest pointEarnRequest) {
        PointEarnCommand pointEarnCommand = new PointEarnCommand(
                pointEarnRequest.getUserId(),
                pointEarnRequest.getPoint(),
                pointEarnRequest.getCause()
        );
        Long pointId = pointEarnUseCase.earn(pointEarnCommand);
        return ResponseEntity.created(URI.create("/points/" + pointId)).build();
    }
}
