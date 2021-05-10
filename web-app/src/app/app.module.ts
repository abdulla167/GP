import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DoctorListComponent } from './doctor-list/doctor-list.component';
import {ProfileComponent} from './profile/profile.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {LoadingSpinnerComponent} from './shared/loading-spinner/loading-spinner.component';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { DatepickerModule, BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import {BabyMonitorComponent} from './profile/baby-monitor/baby-monitor.component';
import {UserInfoComponent} from './profile/user-info/user-info.component';
import {PostsComponent} from './profile/posts/posts.component';
import { TimelineComponent } from './timeline/timeline.component';
import { PostComponent } from './timeline/post/post.component';
import { CreatePostComponent } from './timeline/create-post/create-post.component';
import {CommentComponent} from './timeline/blog-detail/comment/comment.component';
import {QuillEditorBase, QuillEditorComponent, QuillFormat, QuillModule, QuillModules} from 'ngx-quill';
import { BlogDetailComponent } from './timeline/blog-detail/blog-detail.component';
import { CreateBlogComponent } from './timeline/create-blog/create-blog.component';
import { BlogComponent } from './timeline/blog/blog.component';
import {ImageResize} from 'quill-image-resize-module';
import { EditBlogComponent } from './timeline/blog-detail/edit-blog/edit-blog.component';



@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HeaderComponent,
        HomeComponent,
        SignupComponent,
        DoctorListComponent,
        LoadingSpinnerComponent,
        ProfileComponent,
        BabyMonitorComponent,
        UserInfoComponent,
        PostsComponent,
        TimelineComponent,
        PostComponent,
        CreatePostComponent,
        CommentComponent,
        BlogDetailComponent,
        CreateBlogComponent,
        BlogComponent,
        EditBlogComponent
    ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FlexLayoutModule,
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot() ,
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    QuillModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
