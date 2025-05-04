package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.TimelineRequest;
import com.example.myevent_be.dto.response.PageResponse;
import com.example.myevent_be.dto.response.ResponseData;
import com.example.myevent_be.dto.response.ResponseError;
import com.example.myevent_be.dto.response.TimelineResponse;
import com.example.myevent_be.service.TimelineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timelines")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;
    private static final String ERROR_MESSAGE = "errorMessage={}";

    @GetMapping("/list")
    public ResponseData<PageResponse> getTimeline(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                  @Min(5) @RequestParam(defaultValue = "20", required = false) int pageSize) {
        log.info("Request get Timelines, pageNo={}, pageSize={}", pageNo, pageSize);

        try {
            PageResponse<?> timeline = timelineService.getTimelines(pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "success", timeline);
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    // Lấy lịch trình theo id
    @GetMapping("/{id}")
    public ResponseData<TimelineResponse> getTimelineById(@PathVariable String id) {
        log.info("Request get Timeline detail, TimelineId={}", id);

        try {
            TimelineResponse timeline = timelineService.getTimeline(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Timeline", timeline);
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping(value = "/new")
    public ResponseData<TimelineResponse> createTimeline(@Valid @RequestBody TimelineRequest request) {
        log.info("Request add Timeline, {}", request.getDescription());

        try {
            TimelineResponse timelineResponse = timelineService.createTimeline(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Timeline added successfully", timelineResponse);
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Timeline added fail");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateTimeline(@PathVariable String id, @Valid @RequestBody TimelineRequest request) {
        log.info("Request update TimelineId={}", id);
        try {
            timelineService.updateTimeline(request, id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Timeline updated successfully");
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update Timeline fail");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteTimeline(@PathVariable String id) {
        log.info("Request delete TimelineId={}", id);

        try {
            timelineService.deleteTimeline(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Timeline deleted successfully");
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete Timeline fail");
        }
    }
}