package com.example.videocreator.service.impl;

import com.example.videocreator.service.VideoCreatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class VideoCreatorServiceImpl implements VideoCreatorService {

    @Override
    public void createVideo(Map<String, String> files, String correlationId) {
        log.info("All required files received. Starting video creation for correlationId={} with files: {}", correlationId, files);
        log.info("Video creation completed for correlationId={}", correlationId);
    }
}