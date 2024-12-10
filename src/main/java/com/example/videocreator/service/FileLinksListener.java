package com.example.videocreator.service;


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
    private final VideoCreationAggregatorService aggregator;

    @RabbitListener(queues = "${videocreator.rabbit.queue}")
    public void onMessage(String message) {
        try {
            FileMessageDto dto = objectMapper.readValue(message, FileMessageDto.class);
            aggregator.addFile(dto.getCorrelationId(), dto.getFileType(), dto.getFileUrl());
        } catch (Exception e) {
            log.error("Failed to parse message", e);
        }
    }
}