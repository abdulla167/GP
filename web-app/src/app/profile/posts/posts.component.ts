import {Component} from "@angular/core";
import {PostModel} from "../../models/post.model";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'posts-tab',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent{
  logoImg : string = "../../assets/images/logo.svg";
  postImg:any;
  postFile :any;
  postContent : string;
  userPosts : PostModel[]=[];
  theUser : User;

  constructor(public userService: UserService) {
    this.theUser = userService.theuser;
  }


  fileEvent(event){

  }

  addPost(){
    let post = new PostModel(1,this.postContent.slice(), this.postImg, this.postFile,null);
    this.userPosts.unshift(post);
    this.postImg = null;
  }


  imageEvent(event){
    if(!event.target.files[0] || event.target.files[0].length == 0) {
      return;
    }
    let mimeType = event.target.files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (_event) => {
      this.postImg = reader.result;
    }
  }

}
