import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../models/blog.model';
import {UserService} from '../../services/user.service';
import {ImageModel} from '../../models/image.model';
import {CommentModel} from '../../models/comment.model';
import {BlogService} from '../../services/blog.service';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrls: ['./blog-detail.component.css']
})
export class BlogDetailComponent implements OnInit {
  content: BlogModel;
  index: number;
  likeIt: boolean;
  selectedFile: File;
  profileImage;
  defaultImae = '../../assets/images/default.jpg';

  constructor(private userService: UserService, private blogService: BlogService, private activatedRoutes: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.activatedRoutes.params.subscribe(params => {
      this.index = params['index'];
      this.content = this.blogService.getBlogByIndex(this.index);
      console.log(this.content.html);
      for (const like of this.content.likes) {
        if (like.user.username === this.userService.theuser.username) {
          this.likeIt = true;
          break;
        }
      }

      if (this.content.user.profileImg != null) {
        const object = 'data:' + this.content.user.profileImg.type + ';base64,' + this.content.user.profileImg.picByte;
        this.profileImage = this.sanitizer.bypassSecurityTrustUrl(object);
      }else {
        this.profileImage = this.defaultImae;
      }
    });


  }

  onFileChanged(event){
    this.selectedFile = event.target.files[0];
  }
  saveComment(text: HTMLInputElement){

    if (this.selectedFile != null) {
      const reader = new FileReader();
      reader.onload = (e) => {
        // tslint:disable-next-line:one-variable-per-declaration
        const array = new Uint8Array(e.target.result as ArrayBuffer),
          image = this.selectedFile != null ? new ImageModel(0, this.selectedFile.name,
            this.selectedFile.type, btoa(String.fromCharCode.apply(null, array))) : null;
        const comment = new CommentModel(0, text.value, image, null, null, null);

        this.blogService.saveComment(this.content.id, comment).subscribe(response => {
          if (response.status === 200) {
            this.content = response.body as BlogModel;
            console.log(this.content);
          } else {
            console.log('response', response.body);
          }
        });
      };
      reader.readAsArrayBuffer(this.selectedFile);
    }else {
      const comment = new CommentModel(0, text.value, null, null, null, null);

      this.blogService.saveComment(this.content.id, comment).subscribe(response => {
        if (response.status === 200) {
          this.content = response.body as BlogModel;
          console.log(this.content);
        } else {
          console.log('response', response.body);
        }
      });
    }
  }

  onLike(){
    let sendLike = true;
    for (const like of this.content.likes) {
      console.log( "inside it");
      if (like.user.username === this.userService.theuser.username) {

        sendLike = false;
        break;
      }
    }
    if (sendLike)
      this.blogService.addLike(this.content.id).subscribe((response) => {
        if (response.status === 200) {
          console.log('LIke uploaded successfully');
          const blog = (JSON.parse(JSON.stringify(response.body)) as BlogModel );
          this.content = blog;
          this.likeIt = true;
        } else {
          console.log('Image not uploaded successfully');
        }

      });
  }

  deletePost(){
    this.blogService.deleteBlog(this.content.id);
  }
}
