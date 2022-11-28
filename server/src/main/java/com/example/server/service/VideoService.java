package com.example.server.service;

import com.example.server.dto.VideoDto;
import com.example.server.exceptions.EntityNotFoundException;
import com.example.server.model.Video;
import com.example.server.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final FileService fileService;
    private final VideoRepository videoRepository;

    public void uploadVideo(MultipartFile file) {
        var videoUrl = this.fileService.uploadFile(file);

        var video = new Video();
        video.setVideoUrl(videoUrl);

        this.videoRepository.save(video);
    }

    public void editVideo(VideoDto videoDto) {
        var video = this.videoRepository.findById(videoDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Video not found " + videoDto.getId()));
    }
}
