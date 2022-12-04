package com.example.server.service;

import com.example.server.dto.UserInfoDto;
import com.example.server.exceptions.EntityNotFoundException;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;
    private final UserRepository userRepository;

    public void registerUser(String token) {
        var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", token))
                .build();

        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            var responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var body = responseString.body();

            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            var userInfo = objectMapper.readValue(body, UserInfoDto.class);

            var user = new User();
            user.setFirstName(userInfo.getGivenName());
            user.setLastName(userInfo.getFamilyName());
            user.setFullName(userInfo.getName());
            user.setEmailAddress(userInfo.getEmail());
            user.setSub(userInfo.getSub());

            this.userRepository.save(user);
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Exception occurred while registering user.", exception);
        }
    }

    public User getCurrentUser() {
        String sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .getClaim("sub");

        return getUserById(this.userRepository
                .findBySub(sub), "Cannot find user with sub - " + sub);
    }

    public void addToLikedVideos(String videoId, User user) {
        user.addToLikedVideos(videoId);
        this.userRepository.save(user);
    }

    public boolean hasLikedVideo(String videoId, User currentUser) {
        return currentUser
                .getLikedVideos()
                .stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean hasDislikedVideo(String videoId, User currentUser) {
        return currentUser
                .getDislikedVideos()
                .stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId, User currentUser) {
        currentUser.removeFromLikedVideos(videoId);
        this.userRepository.save(currentUser);
    }

    public void removeFromDislikedVideos(String videoId, User currentUser) {
        currentUser.removeFromDislikedVideos(videoId);
        this.userRepository.save(currentUser);
    }

    public void addToDislikedVideos(String videoId, User currentUser) {
        currentUser.addToDislikedVideos(videoId);
        this.userRepository.save(currentUser);
    }

    public void addVideoToHistory(String videoId) {
        var currentUser = this.getCurrentUser();
        currentUser.addToVideoHistory(videoId);
        this.userRepository.save(currentUser);
    }

    public void subscribeToUser(String userId) {
        var currentUser = this.getCurrentUser();
        currentUser.subscribeToUser(userId);

        var user = getUserById(this.userRepository.findById(userId), "User not found");

        user.addToSubscribers(currentUser.getId());

        this.userRepository.save(currentUser);
        this.userRepository.save(user);
    }

    public void unsubscribeFromUser(String userId) {
        var currentUser = this.getCurrentUser();
        currentUser.removeSubscription(userId);

        var user = getUserById(this.userRepository.findById(userId), "User not found");
        user.removeFromSubscribers(currentUser.getId());

        this.userRepository.save(currentUser);
        this.userRepository.save(user);
    }

    public Set<String> getUserHistory(String userId) {
        return getUserById(this.userRepository.findById(userId), "User not found")
                .getVideoHistory();
    }

    private User getUserById(Optional<User> userRepository, String User_not_found) {
        return userRepository
                .orElseThrow(() -> new EntityNotFoundException(User_not_found));
    }
}
