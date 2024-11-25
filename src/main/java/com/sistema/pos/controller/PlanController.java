package com.sistema.pos.controller;


import com.sistema.pos.entity.Plan;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.PlanService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Plan>>> listarPlan() {
        List<Plan> plans=planService.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Plan>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(plans)
                        .build(),
                HttpStatus.OK
        );
    }
}
