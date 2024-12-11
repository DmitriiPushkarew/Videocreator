package com.example.videocreator.service.impl;


import com.example.videocreator.dto.FileMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileLinksListener {
    private final ObjectMapper objectMapper;
    private final FileLinkAggregatorServiceServiceImpl aggregator;

    @RabbitListener(queues = "${videocreator.rabbit.queue}")
    public void onMessage(String message) {
        try {
            FileMessageDto dto = objectMapper.readValue(message, FileMessageDto.class);
            aggregator.addFileLink(dto.getCorrelationId(), dto.getFileType(), dto.getFileUrl());
            log.info("Message processed for correlationId={}", dto.getCorrelationId());
        } catch (Exception e) {
            log.error("Failed to parse and process message: {}", message, e);
        }
    }
}