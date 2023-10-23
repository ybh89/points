package com.ybh.points.adapter.in.web;

import com.ybh.points.application.port.in.EarnPointCommand;
import com.ybh.points.application.port.in.EarnPointUseCase;
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
class EarnPointController {
    private final EarnPointUseCase earnPointUseCase;

    @Autowired
    EarnPointController(EarnPointUseCase earnPointUseCase) {
        this.earnPointUseCase = earnPointUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> earnPoint(@RequestBody @Valid EarnPointRequest earnPointRequest) {
        EarnPointCommand earnPointCommand = new EarnPointCommand(
                earnPointRequest.getUserId(),
                earnPointRequest.getPoint(),
                earnPointRequest.getCause()
        );
        Long pointId = earnPointUseCase.earn(earnPointCommand);
        return ResponseEntity.created(URI.create("/points/" + pointId)).build();
    }
}
