package org.example.videocreator.service.impl;

import org.example.videocreator.service.FileAggregatorService;
import org.example.videocreator.service.VideoCreatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileLinkAggregatorServiceServiceImpl implements FileAggregatorService {
    private final VideoCreatorService videoCreatorService;
    private final Map<String, Map<String, String>> fileStorage = new ConcurrentHashMap<>();
    private final Set<String> requiredFileTypes = Set.of("voiceover", "background", "animation");

    public synchronized void addFileLink(String correlationId, String fileType, String fileUrl) {
        fileStorage.putIfAbsent(correlationId, new ConcurrentHashMap<>());
        Map<String, String> files = fileStorage.get(correlationId);

        files.put(fileType, fileUrl);
        log.info("Added file. correlationId={}, fileType={}, fileUrl={}", correlationId, fileType, fileUrl);

        if (files.keySet().containsAll(requiredFileTypes)) {
            videoCreatorService.createVideo(files, correlationId);
            fileStorage.remove(correlationId);
        }

    }

}
