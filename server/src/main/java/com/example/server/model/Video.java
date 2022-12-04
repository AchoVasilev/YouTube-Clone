package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "video")
@Data
@AllArgsConstructor
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes;
    private AtomicInteger dislikes;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount;
    private String thumbnailUrl;
    private List<Comment> comments;

    public Video() {
        this.likes = new AtomicInteger(0);
        this.dislikes = new AtomicInteger(0);
        this.viewCount = new AtomicInteger(0);
    }

    public void incrementLikes() {
        this.likes.incrementAndGet();
    }

    public void decrementLikes() {
        this.likes.decrementAndGet();
    }

    public void incrementDislikes() {
        this.dislikes.incrementAndGet();
    }

    public void decrementDislikes() {
        this.dislikes.decrementAndGet();
    }

    public void incrementViewCount() {
        this.viewCount.incrementAndGet();
    }
}
