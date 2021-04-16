<<<<<<< HEAD
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
=======
import {Component} from '@angular/core';
import {Post} from '../../models/post.model';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user.model';

@Component({
  selector: 'app-posts-tab',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent{
  logoImg = '../../assets/images/logo.svg';
  postImg: any;
  postFile: any;
  postContent: string;
  userPosts: Post[] = [];
  theUser: User;

  constructor(public userService: UserService) {
    this.theUser = userService.theuser;
  }


  fileEvent(event){

  }

  addPost(){
    const post = new Post(1, this.postContent.slice(), this.postImg, this.postFile, null,null, null);
    this.userPosts.unshift(post);
    this.postImg = null;
  }


  imageEvent(event){
    if (!event.target.files[0] || event.target.files[0].length == 0) {
      return;
    }
    const mimeType = event.target.files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (_event) => {
      this.postImg = reader.result;
    };
  }


}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
