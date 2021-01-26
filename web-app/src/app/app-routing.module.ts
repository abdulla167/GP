import {NgModule} from "@angular/core";
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {SignupComponent} from "./auth/signup/signup.component";
import {LoginComponent} from "./auth/login/login.component";
import {DoctorListComponent} from "./doctor-list/doctor-list.component";

const appRoutes  : Routes = [
  {path: 'home', component: HomeComponent},
  {path : "signup", component: SignupComponent},
  {path: "login", component: LoginComponent},
  {path: "bookdoctor", component: DoctorListComponent}
]

@NgModule({
  imports:[RouterModule.forRoot(appRoutes)],
  exports:[RouterModule]
})
export class AppRoutingModule{

}
