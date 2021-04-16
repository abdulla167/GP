import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {DoctorListComponent} from './doctor-list/doctor-list.component';
import {ProfileComponent} from "./profile/profile.component";
import {CreatePostComponent} from './timeline/create-post/create-post.component';
import {TimelineComponent} from './timeline/timeline.component';

const appRoutes: Routes = [
  {path: 'home', component: HomeComponent},
  {path : 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'bookdoctor', component: DoctorListComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'timeline', component: TimelineComponent},
  {path: 'new_post', component: CreatePostComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
