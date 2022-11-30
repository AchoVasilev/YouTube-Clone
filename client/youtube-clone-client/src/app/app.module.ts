import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatOptionModule, MatSelectModule, MatToolbarModule } from '@angular/material';
import { MatChipsModule } from '@angular/material/chips'
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserModule } from '@angular/platform-browser';
import { NgxFileDropModule } from 'ngx-file-drop';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { HeaderComponent } from './header/header.component';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    SaveVideoDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    ReactiveFormsModule,
    MatChipsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
