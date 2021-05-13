import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import 'flatpickr/dist/flatpickr.css';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DoctorListComponent } from './doctor-list/doctor-list.component';
import {ProfileComponent} from './profile/profile.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
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
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatTabsModule} from '@angular/material/tabs';
import { CalendarComponent } from './profile/calendar/calendar.component';
import { TempChartComponent } from './profile/baby-monitor/temp-chart/temp-chart.component';
import { RespirChartComponent } from './profile/baby-monitor/respir-chart/respir-chart.component';
import { HeartRateChartComponent } from './profile/baby-monitor/heart-rate-chart/heart-rate-chart.component';
import { MatDialogModule } from '@angular/material/dialog';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CommonModule } from '@angular/common';
import { FlatpickrModule } from 'angularx-flatpickr';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { EventChoiceComponent } from './profile/calendar/event-choice/event-choice.component';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonToggleModule} from '@angular/material/button-toggle';




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
    CalendarComponent,
    TempChartComponent,
    RespirChartComponent,
    HeartRateChartComponent,
    EventChoiceComponent
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
    CollapseModule.forRoot(),
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatTabsModule,
    MatDialogModule,
    CommonModule,
    FormsModule,
    NgbModalModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonToggleModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
