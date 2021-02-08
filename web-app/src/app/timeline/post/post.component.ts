import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../models/post.model';
import {PostService} from '../../services/post.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post: Post;
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
