import { Component, Input, OnInit } from '@angular/core';
import { VideoDto } from '../model/video-dto';

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.css']
})
export class VideoCardComponent {

  @Input()
  video: VideoDto | undefined;

  constructor() { }

}
