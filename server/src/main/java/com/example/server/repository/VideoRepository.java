package com.example.server.repository;

import com.example.server.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository  extends MongoRepository<Video, String> {

}
