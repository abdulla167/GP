import {Component} from "@angular/core";
import {PostModel} from "../models/post.model";
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";

@Component({
  selector: 'user-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent{
  mainPage : string = 'about';
  theUser : User;
  profileImg : any = "../../assets/images/default.jpg";
  selectedFile: File;
  message: string;
  imageName: any;

  constructor(public userService :UserService) {
    this.theUser = userService.theuser;
    this.theUser.profileImg = this.profileImg;
  }

  toggleNav(content : string){
    this.mainPage = content;
  }

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
      this.userService.theuser.profileImg = this.profileImg;
    }
  }


}
