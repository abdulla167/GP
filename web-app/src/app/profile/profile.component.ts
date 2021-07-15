import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {PostModel} from "../models/post.model";
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {MatDialog} from "@angular/material/dialog";
import {AdditionalInfoComponent} from "../auth/additional-info/additional-info.component";

@Component({
  selector: 'user-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements  OnInit{
  mainPage : string = 'about';
  pregnancyAccomplishment = 40;
  theUser : User;
  profileImg : any = "../../assets/images/newimg2.jpeg";
  selectedFile: File;
  message: string;
  imageName: any;
  calender: any = [{name : 'First task', status : true}, {name : 'Second task', status : false}];
  tab : number = 0;

  constructor(public userService :UserService, private dialog: MatDialog, ) {}

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
    this.userService.getUser().subscribe(resp => {
      this.userService.theUser = resp.body;
      this.theUser = this.userService.theUser;
      if (this.userService.theUser.additionalInfo == false){
        const dialogConfig = {
          autoFocus : true,
          disableClose : true
        };
        this.dialog.open(AdditionalInfoComponent, dialogConfig);
      }
    })
  }

}
