<<<<<<< HEAD
import {Component, Input, OnInit} from '@angular/core';
import {PostModel} from '../../models/post.model';
import {PostService} from '../../services/post.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post: PostModel;
  image;
  time;
  @Input() id: number;
  constructor(private  postService: PostService, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.post = this.postService.getPostByIndex(this.id);
    // console.log(this.post.date.t);
    const object  = 'data:' + this.post.image.type + ';base64,' + this.post.image.picByte;
    this.image = this.sanitizer.bypassSecurityTrustUrl(object);
    const curentTime = new Date();

    this.time = this.timeFromSeconds((Math.floor(curentTime.getTime() - new Date(this.post.date).getTime() ) / 1000) );

  }
  timeFromSeconds(seconds: number){
    if (seconds >= (7 * 24 * 60 * 60 )){
      if ( (seconds >= (7 * 24 * 60 * 60 * 2)) || (seconds % (7 * 24 * 60 * 60) < (24 * 60 * 60))) {
        return Math.floor(seconds / 7 * 24 * 60 * 60) + 'w';
      }else {
          return Math.floor(seconds / (7 * 24 * 60 * 60)) + 'w' + Math.floor((seconds % (7 * 24 * 60 * 60)) / (24 * 60 * 60)) + 'd';
      }
    }else if (seconds >= (24 * 60 * 60)){
      if ((seconds >= (24 * 60 * 60 * 2)) || ((seconds % (24 * 60 * 60) ) < (60 * 60))){
        return Math.floor(seconds / (24 * 60 * 60)) + 'd';
      }else {
        return Math.floor(seconds / ( 24 * 60 * 60)) + 'd' + Math.floor((seconds %  (24 * 60 * 60)) / (60 * 60)) + 'h';
      }
    } else if (seconds >= (60 * 60)){
      if ((seconds >= (60 * 60 * 2 )) || ((seconds % (60 * 60 )) < 60)){
        return Math.floor(seconds / (60 * 60)) + 'h';
      }else {
        return Math.floor(seconds /  (60 * 60)) + 'h' + Math.floor((seconds %  (60 * 60)) /  60) + 'm';
      }
    }else if (seconds >= 60){
      return Math.floor(seconds / 60) + 'm';
    }else {
      return seconds + 's';
    }
  }
}
=======
import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../models/post.model';
import {PostService} from '../../services/post.service';
import {DomSanitizer} from '@angular/platform-browser';
import {CommentModel} from '../../models/comment.model';
import {ImageModel} from '../../models/image.model';
import {LikeModel} from '../../models/like.model';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post: Post;
  image;
  time;
  selectedFile: File;
  defaultImae = '../../assets/images/default.jpg';
  likeIt: boolean;
  @Input() id: number;
  constructor(private  postService: PostService, private sanitizer: DomSanitizer, private userService: UserService) {
  }

  ngOnInit(): void {
    this.post = this.postService.getPostByIndex(this.id);
    for( const like of this.post.likes){
      if( like.user.username === this.userService.theuser.username ) {
        this.likeIt = true;
        break;
      }

    }
    // console.log(this.post.date.t);
    const object  = 'data:' + this.post.image.type + ';base64,' + this.post.image.picByte;
    this.image = this.sanitizer.bypassSecurityTrustUrl(object);
    const curentTime = new Date();

    this.time = this.timeFromSeconds((Math.floor(curentTime.getTime() - new Date(this.post.date).getTime() ) / 1000) );

  }
  onFileChanged(event){
    this.selectedFile = event.target.files[0];
  }
  saveComment(text: HTMLInputElement){

    if (this.selectedFile != null) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const array = new Uint8Array(e.target.result as ArrayBuffer),
          image = this.selectedFile != null ? new ImageModel(0, this.selectedFile.name,
            this.selectedFile.type, btoa(String.fromCharCode.apply(null, array))) : null;
        const comment = new CommentModel(0, text.value, image, null, null);

        this.postService.saveComment(this.post.id, comment).subscribe(response => {
          if (response.status == 200) {
            this.post = response.body as Post;
            console.log(this.post);
          } else {
            console.log(response.body);
          }
        });
      };
      reader.readAsArrayBuffer(this.selectedFile);
    }else {
      const comment = new CommentModel(0, text.value, null, null, null);

      this.postService.saveComment(this.post.id, comment).subscribe(response => {
        if (response.status == 200) {
          this.post = response.body as Post;
          console.log(this.post);
        } else {
          console.log(response.body);
        }
      });
    }


  }
  timeFromSeconds(seconds: number){
    if (seconds >= (7 * 24 * 60 * 60 )){
      if ( (seconds >= (7 * 24 * 60 * 60 * 2)) || (seconds % (7 * 24 * 60 * 60) < (24 * 60 * 60))) {
        return Math.floor(seconds / 7 * 24 * 60 * 60) + 'w';
      }else {
          return Math.floor(seconds / (7 * 24 * 60 * 60)) + 'w' + Math.floor((seconds % (7 * 24 * 60 * 60)) / (24 * 60 * 60)) + 'd';
      }
    }else if (seconds >= (24 * 60 * 60)){
      if ((seconds >= (24 * 60 * 60 * 2)) || ((seconds % (24 * 60 * 60) ) < (60 * 60))){
        return Math.floor(seconds / (24 * 60 * 60)) + 'd';
      }else {
        return Math.floor(seconds / ( 24 * 60 * 60)) + 'd' + Math.floor((seconds %  (24 * 60 * 60)) / (60 * 60)) + 'h';
      }
    } else if (seconds >= (60 * 60)){
      if ((seconds >= (60 * 60 * 2 )) || ((seconds % (60 * 60 )) < 60)){
        return Math.floor(seconds / (60 * 60)) + 'h';
      }else {
        return Math.floor(seconds /  (60 * 60)) + 'h' + Math.floor((seconds %  (60 * 60)) /  60) + 'm';
      }
    }else if (seconds >= 60){
      return Math.floor(seconds / 60) + 'm';
    }else {
      return seconds + 's';
    }
  }
  onLike(){
    let sendLike = true;
    for (const like of this.post.likes) {
      console.log( "inside it");
      if (like.user.username === this.userService.theuser.username) {

        sendLike = false;
        break;
      }
    }
    if (sendLike)
      this.postService.addLike(this.post.id, this.id).subscribe((response) => {
        if (response.status === 200) {
          console.log('LIke uploaded successfully');
          const post = (JSON.parse(JSON.stringify(response.body)) as Post );
          this.post = post;
          this.likeIt = true;
        } else {
          console.log('Image not uploaded successfully');
        }

      });
  }
}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
