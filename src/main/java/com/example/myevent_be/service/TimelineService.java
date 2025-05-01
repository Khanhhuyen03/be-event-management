package com.example.myevent_be.service;

import com.example.myevent_be.dto.request.TimelineRequest;
import com.example.myevent_be.dto.response.PageResponse;
import com.example.myevent_be.dto.response.TimelineResponse;
import com.example.myevent_be.entity.TimeLine;
import com.example.myevent_be.exception.ResourceNotFoundException;
import com.example.myevent_be.mapper.PageMapper;
import com.example.myevent_be.mapper.TimelineMapper;
import com.example.myevent_be.repository.RentalRepository;
import com.example.myevent_be.repository.TimelineRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TimelineService {

    TimelineRepository timelineRepository;
    TimelineMapper timelineMapper;
    PageMapper pageMapper;
    RentalRepository rentalRepository;


    public TimelineResponse createTimeline(TimelineRequest request) {

        TimeLine timeline = timelineMapper.toTimeline(request);

        log.info("Received TimelineRequest: {}", request);

        timelineRepository.save(timeline);
        return timelineMapper.toTimelineResponse(timeline);
    }

    public PageResponse getTimelines(int pageNo, int pageSize) {
        int p=0;
        if(pageNo>0){
            p=pageNo-1;
        }
        Page<TimeLine> page = timelineRepository.findAll(PageRequest.of(p, pageSize));
        return pageMapper.toPageResponse(page,timelineMapper::toTimelineResponse);
    }

    public TimeLine getTimelineById(String id) {
        return timelineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Timeline not found with rentalid: " + id));
    }


    public TimelineResponse updateTimeline(TimelineRequest request, String id){
        TimeLine timeline= getTimelineById(id);

        timelineMapper.upDateTimeline(timeline,request);

        return timelineMapper.toTimelineResponse(timelineRepository.save(timeline));
    }

    public TimelineResponse getTimeline(@PathVariable String id){
        TimeLine timeline= getTimelineById(id);
        return timelineMapper.toTimelineResponse(timeline);
    }

    public void deleteTimeline(String id) {
        TimeLine timeline= getTimelineById(id);
       timelineRepository.delete(timeline);
    }

}