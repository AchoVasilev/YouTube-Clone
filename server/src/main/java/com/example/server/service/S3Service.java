package com.example.server.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {
    private final AmazonS3Client amazonS3Client;

    @Override
    public String uploadFile(MultipartFile file) {
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + "." + fileExtension;

        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        var bucketName = "ytclonebg";
        try {
            this.amazonS3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception has occurred while uploading th file.");
        }

        this.amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        var url = this.amazonS3Client.getResourceUrl(bucketName, key);

        return url;
    }
}
