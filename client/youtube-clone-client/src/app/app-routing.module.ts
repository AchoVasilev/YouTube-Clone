import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeaturedComponent } from './featured/featured.component';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';
import { LikedVideosComponent } from './liked-videos/liked-videos.component';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { VideoDetailsComponent } from './video-details/video-details.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full',
    redirectTo: '/featured',
    children: [
      {
        path: 'subscriptions',
        component: SubscriptionsComponent,
        pathMatch: 'full'
      },
      {
        path: 'featured',
        component: FeaturedComponent,
        pathMatch: 'full'
      },
      {
        path: 'history',
        component: HistoryComponent,
        pathMatch: 'full'
      },
      {
        path: 'liked-videos',
        component: LikedVideosComponent,
        pathMatch: 'full'
      },
    ]
  },
  {
    path: 'upload-video',
    component: UploadVideoComponent,
    pathMatch: 'full'
  },
  {
    path: 'save-video-details/:videoId',
    component: SaveVideoDetailsComponent,
    pathMatch: 'full'
  },
  {
    path: 'video-details/:videoId',
    component: VideoDetailsComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
