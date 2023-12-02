package com.example.controller;

import com.example.models.entity.MailSchedule;
import com.example.models.request.MessageRequest;
import com.example.models.request.Request;
import com.example.models.response.ApiResponseOne;
import com.example.service.MailScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/schedules")
@SecurityRequirement(name = "auth")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private MailScheduleService mailScheduleService;

    @PostMapping("create")
    @Operation(summary = "create schedule for specific user ( use 1 -> 23 h ) ")
    public ResponseEntity<?> createSchedule(@RequestBody Request request) {
        return ResponseEntity.ok().body(mailScheduleService.createSchedule(request));
    }

    @PostMapping("scheduleForAll")
    @Operation(summary = "create schedule for all ( use 1 -> 23 h ) ")
    public ResponseEntity<?> createScheduleForAllUser(@RequestBody MessageRequest request) {
        return ResponseEntity.ok().body(mailScheduleService.createScheduleForAllUser(request));
    }

    @GetMapping("")
    @Operation(summary = "get all schedule")
    public ApiResponseOne<?> getSchedules(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Page<MailSchedule> mailSchedules = mailScheduleService.getSchedules(page, size);
        return ApiResponseOne.builder()
                .message("get Email Reciver Successfully")
                .payload(mailSchedules.getContent())
                .total(mailSchedules.getTotalElements())
                .status(200)
                .build();
    }

    @GetMapping("{userId}")
    @Operation(summary = "get all schedule by user id")
    public ResponseEntity<?> getAllScheduleByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok().body(mailScheduleService.getAllSchedulesByUserId(String.valueOf(userId), pageNo, pageSize));
    }

    @PutMapping("{scheduleId}")
    @Operation(summary = "update schedule by id")
    public ResponseEntity<?> updateScheduleById(@PathVariable Long scheduleId, @RequestBody Request request) {
        return ResponseEntity.ok().body(mailScheduleService.updateScheduleById(scheduleId, request));
    }

    @DeleteMapping("{scheduleId}")
    @Operation(summary = "delete schedule by id")
    public ResponseEntity<?> deleteSchedule(@PathVariable(value = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok().body(mailScheduleService.deleteScheduleById(scheduleId));
    }
}
