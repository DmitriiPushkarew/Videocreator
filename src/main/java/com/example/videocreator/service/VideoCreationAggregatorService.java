package com.example.videocreator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class VideoCreationAggregatorService {

    private final Map<String, Map<String, String>> fileStorage = new ConcurrentHashMap<>();
    private final Set<String> requiredFileTypes = Set.of("voiceover", "background", "animation");

    public synchronized void addFile(String correlationId, String fileType, String fileUrl) {
        fileStorage.putIfAbsent(correlationId, new ConcurrentHashMap<>());
        Map<String, String> files = fileStorage.get(correlationId);
        files.put(fileType, fileUrl);
        log.info("Received file {} of type {} for correlationId {}", fileUrl, fileType, correlationId);
        if (files.keySet().containsAll(requiredFileTypes)) {
            log.info("All files received for correlationId {}. Starting video creation...", correlationId);
            startVideoCreation(files);
            fileStorage.remove(correlationId);
        }
    }

    private void startVideoCreation(Map<String, String> files) {
        log.info("Video creation started with files: {}", files);
    }
}
