package org.example.videocreator.service;

import java.util.Map;

public interface VideoCreatorService {

    void createVideo(Map<String, String> files, String correlationId);

}
