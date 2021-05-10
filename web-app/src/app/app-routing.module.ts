import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {DoctorListComponent} from './doctor-list/doctor-list.component';
import {ProfileComponent} from './profile/profile.component';
import {TimelineComponent} from './timeline/timeline.component';
import {CreateBlogComponent} from './timeline/create-blog/create-blog.component';
import {BlogDetailComponent} from './timeline/blog-detail/blog-detail.component';
import {EditBlogComponent} from './timeline/blog-detail/edit-blog/edit-blog.component';

const appRoutes: Routes = [
  {path: 'home', component: HomeComponent},
  {path : 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'bookdoctor', component: DoctorListComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'timeline', component: TimelineComponent},
  {path: 'newBlog', component: CreateBlogComponent},
  {path: 'editBlog/:index', component: EditBlogComponent},
  {path: 'blog/:index', component: BlogDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
