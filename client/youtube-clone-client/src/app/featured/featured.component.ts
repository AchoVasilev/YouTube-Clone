import { Component, OnInit } from '@angular/core';
import { VideoDto } from '../model/video-dto';
import { VideoService } from '../service/video.service';

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css']
})
export class FeaturedComponent implements OnInit {

  featuredVideos: VideoDto[] = [];

  constructor(private videoService: VideoService) { }

  ngOnInit(): void {
    this.videoService.getAllVideos()
      .subscribe(data => {
        this.featuredVideos = data;
      });
  }

}
