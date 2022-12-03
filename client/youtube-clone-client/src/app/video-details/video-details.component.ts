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

  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];

    this.videoService.getVideo(this.videoId)
      .subscribe(data => {
        this.videoUrl = data.videoUrl;
        this.videoAvailable = true;
      });
  }

}
