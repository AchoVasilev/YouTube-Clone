import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoResponse } from '../model/uploadVideoResponse';
import { VideoDto } from '../model/video-dto';
@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(file: File): Observable<UploadVideoResponse> {
    const formData = new FormData();
    formData.append("file", file, file.name);

    return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos", formData);
  }

  uploadThumbnail(file: File, videoId: string): Observable<string> {
    const formData = new FormData();
    formData.append("file", file, file.name);
    formData.append("videoId", videoId);

    return this.httpClient.post("http://localhost:8080/api/videos/thumbnail", formData, {
      responseType: 'text'
    });
  }

  getVideo(videoId: string): Observable<VideoDto> {
    return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId);
  }

  saveVideo(videoMetadata: VideoDto): Observable<VideoDto> {
    return this.httpClient.put<VideoDto>("http://localhost:8080/api/videos", videoMetadata);
  }

  getAllVideos(): Observable<VideoDto[]> {
    return this.httpClient.get<VideoDto[]>("http://localhost:8080/videos");
  }
}
