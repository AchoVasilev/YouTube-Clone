package com.example.server.service;

import com.example.server.dto.UploadVideoResponse;
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
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile file) {
        var videoUrl = this.fileService.uploadFile(file);

        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = this.videoRepository.save(video);

        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
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

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var video = this.getVideo(videoId);
        var thumbnailUrl = this.fileService.uploadFile(file);

        video.setThumbnailUrl(thumbnailUrl);

        this.videoRepository.save(video);

        return thumbnailUrl;
    }

    private Video getVideo(String videoId) {
        return this.videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found " + videoId));
    }

    public VideoDto getDetails(String videoId) {
        var video = this.getVideo(videoId);

        this.incrementViewCount(video);
        this.userService.addVideoToHistory(video.getId());

        return this.mapVideo(video);
    }

    public VideoDto likeVideo(String videoId) {
        var video = this.getVideo(videoId);

        var currentUser = this.userService.getCurrentUser();

        if (this.userService.hasLikedVideo(videoId, currentUser)) {
            video.decrementLikes();
            this.userService.removeFromLikedVideos(videoId, currentUser);
        } else if (this.userService.hasDislikedVideo(videoId, currentUser)) {
            video.decrementDislikes();
            this.userService.removeFromDislikedVideos(videoId, currentUser);

            video.incrementLikes();
            this.userService.addToLikedVideos(videoId, currentUser);
        } else {
            video.incrementLikes();
            this.userService.addToLikedVideos(videoId, currentUser);
        }

        this.videoRepository.save(video);

        return this.mapVideo(video);
    }

    public VideoDto dislikeVideo(String videoId) {
        var video = this.getVideo(videoId);

        var currentUser = this.userService.getCurrentUser();

        if (this.userService.hasDislikedVideo(videoId, currentUser)) {
            video.decrementDislikes();
            this.userService.removeFromDislikedVideos(videoId, currentUser);
        } else if (this.userService.hasLikedVideo(videoId, currentUser)) {
            video.decrementLikes();
            this.userService.removeFromLikedVideos(videoId, currentUser);

            video.incrementDislikes();
            this.userService.addToDislikedVideos(videoId, currentUser);
        } else {
            video.incrementDislikes();
            this.userService.addToDislikedVideos(videoId, currentUser);
        }

        this.videoRepository.save(video);

        return this.mapVideo(video);
    }

    private void incrementViewCount(Video video) {
        video.incrementViewCount();
        this.videoRepository.save(video);
    }

    private VideoDto mapVideo(Video video) {
        return new VideoDto(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getTags(),
                video.getVideoUrl(),
                video.getVideoStatus(),
                video.getThumbnailUrl(),
                video.getLikes().get(),
                video.getDislikes().get(),
                video.getViewCount().get()
        );
    }
}
