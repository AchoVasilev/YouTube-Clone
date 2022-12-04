import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../service/video.service';

@Component({
  selector: 'app-video-details',
  templateUrl: './video-details.component.html',
  styleUrls: ['./video-details.component.css']
})
export class VideoDetailsComponent {
  videoId: string = '';
  videoUrl: string = '';
  videoAvailable: boolean = false;
  videoTitle: string = '';
  videoDescription: string = '';
  tags: string[] = [];
  likesCount: number = 0;
  dislikesCount: number = 0;
  viewsCount: number = 0;
  showSubscribeButton: boolean = true;
  showUnSubscribeButton: boolean = !this.showSubscribeButton;

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];

    this.videoService.getVideo(this.videoId)
      .subscribe(data => {
        this.videoUrl = data.videoUrl;
        this.videoTitle = data.title;
        this.videoDescription = data.description;
        this.tags = data.tags;
        this.likesCount = data.likesCount;
        this.dislikesCount = data.dislikesCount;
        this.viewsCount = data.viewsCount;

        this.videoAvailable = true;
      });
  }

  likeVideo() {
    this.videoService.likeVideo(this.videoId)
      .subscribe(data => {
        this.likesCount = data.likesCount;
        this.dislikesCount = data.dislikesCount;
      });
  }

  dislikeVideo() {
    this.videoService.dislikeVideo(this.videoId)
      .subscribe(data => {
        this.likesCount = data.likesCount;
        this.dislikesCount = data.dislikesCount;
      });
  }

  subscribeToUser() {

  }
}
