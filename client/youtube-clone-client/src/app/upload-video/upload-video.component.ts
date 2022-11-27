import { Component } from '@angular/core';
import { FileSystemFileEntry, NgxFileDropEntry } from 'ngx-file-drop';
import { VideoService } from '../service/video.service';

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent {

  public files: NgxFileDropEntry[] = [];
  public fileUploaded = false;
  private fileEntry: FileSystemFileEntry | undefined;

  constructor(private videoService: VideoService) { }

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;

    for (const droppedFile of files) {

      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;

        this.fileEntry.file((file: File) => {
          console.log(droppedFile.relativePath, file);

          this.fileUploaded = true;
        })
      } else {
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any) {
    console.log(event);
  }

  public fileLeave(event: any) {
    console.log(event);
  }

  uploadVideo() {
    if (this.fileEntry !== undefined) {

      this.fileEntry.file(file => {
        this.videoService.uploadVideo(file)
          .subscribe(data => {
            console.log("Success");
          });
      });
    }
  }
}
