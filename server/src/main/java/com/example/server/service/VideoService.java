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

    public VideoDto editVideo(VideoDto videoDto) {
        var video = this.getVideo(videoDto.getId());

        video.setTitle(videoDto.getTitle());
        video.setDescription(videoDto.getDescription());
        video.setTags(videoDto.getTags());
        video.setThumbnailUrl(videoDto.getThumbnailUrl());
        video.setVideoStatus(videoDto.getVideoStatus());

        this.videoRepository.save(video);

        return videoDto;
    }

    public void uploadThumbnail(MultipartFile file, String videoId) {
        var video = this.getVideo(videoId);
        var thumbnailUrl = this.fileService.uploadFile(file);

        video.setThumbnailUrl(thumbnailUrl);

        this.videoRepository.save(video);
    }

    private Video getVideo(String videoId) {
        return this.videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found " + videoId));
    }
}
