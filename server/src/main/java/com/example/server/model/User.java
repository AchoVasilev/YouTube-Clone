package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "user")
@Data
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private Set<String> videoHistory;
    private Set<String> likedVideos;
    private Set<String> dislikedVideos;

    public void subscribeToUser(String userId) {
        this.subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String id) {
        this.subscribers.add(id);
    }

    public User() {
        this.likedVideos = ConcurrentHashMap.newKeySet();
        this.dislikedVideos = ConcurrentHashMap.newKeySet();
        this.videoHistory = ConcurrentHashMap.newKeySet();
        this.subscribers = ConcurrentHashMap.newKeySet();
        this.subscribedToUsers = ConcurrentHashMap.newKeySet();
    }

    public void addToLikedVideos(String videoId) {
        this.likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        this.likedVideos.remove(videoId);
    }

    public void removeFromDislikedVideos(String videoId) {
        this.dislikedVideos.remove(videoId);
    }

    public void addToDislikedVideos(String videoId) {
        this.dislikedVideos.add(videoId);
    }

    public void addToVideoHistory(String id) {
        this.videoHistory.add(id);
    }
}
