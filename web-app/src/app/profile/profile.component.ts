import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {PostModel} from "../models/post.model";
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {MatDialog} from "@angular/material/dialog";
import {AdditionalInfoComponent} from "../auth/additional-info/additional-info.component";
import {EventsService} from "../services/events.service";
import {Router} from "@angular/router";
import {TokenService} from "../services/Token.service";
import {CalendarEvent} from "angular-calendar";
import {EventModel} from "../models/event.model";

@Component({
  selector: 'user-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements  OnInit{
  mainPage : string = 'about';
  pregnancyAccomplishment = null;
  theUser : User;
  profileImg : any = "../../assets/images/newimg2.jpeg";
  selectedFile: File;
  message: string;
  imageName: any;
  calender: any = [{name : 'First task', status : true}, {name : 'Second task', status : false}];
  tab : number = 0;
  upcomingEvents : EventModel[] = []

  constructor(public userService :UserService, private dialog: MatDialog, private eventService : EventsService, private tokenService : TokenService, private router : Router)
  {}

  updateProfileImg(event){
    if(!event.target.files[0] || event.target.files[0].length == 0) {
      return;
    }
    let mimeType = event.target.files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    this.userService.saveProfileImg(event.target.files[0]).subscribe((response) => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
    );
    reader.onload = (_event) => {
      this.profileImg = reader.result;
      this.userService.theUser.profileImg = this.profileImg;
    };
  }

  ngOnInit(): void {
    if (this.tokenService.getToken() == null){
      this.router.navigate(['/login']);
    } else {
      this.eventService.addedEvents.subscribe(data => {
        this.upcomingEvents = [...this.eventService.upcomingEvents];
      })
      this.userService.getUser().subscribe(resp => {
        this.userService.theUser = resp.body;
        this.theUser = this.userService.theUser;
        let pregnancyDate = this.userService.theUser.pregnancyDate;
        if (this.userService.theUser.additionalInfo == false){
          const dialogConfig = {
            autoFocus : true,
            disableClose : true
          };
          this.dialog.open(AdditionalInfoComponent, dialogConfig);
        }
        if (pregnancyDate != null){
          let currentDate = new Date();
          let dateOfPregnancy = new Date(pregnancyDate);
          let dateOfbirth = new Date(dateOfPregnancy.getTime() + 23328000000);
          let diff = Math.ceil(Math.abs((currentDate.getTime() - dateOfPregnancy.getTime())/ (1000 * 3600 * 24)));
          let diff2 = Math.ceil(Math.abs( (dateOfbirth.getTime() - dateOfPregnancy.getTime()) / (1000 * 3600 * 24)));
          this.pregnancyAccomplishment = Math.round((diff / diff2) * 100 * 100) / 100;
        }
      })
    }
  }
}
