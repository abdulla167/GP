import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../../models/blog.model';
import {DomSanitizer} from '@angular/platform-browser';
import {CreateBlogComponent} from '../../create-blog/create-blog.component';
import {MatDialog} from '@angular/material/dialog';
import {BlogDetailComponent} from './blog-detail/blog-detail.component';
import {BlogService} from '../../../services/Blog.service';
import {LikeModel} from '../../../models/like.model';
import {UserService} from '../../../services/user.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {
  @Input() blog: BlogModel;
  @Input() index: number;
  dialogRef;
  likeIt: boolean = false ;
  like: LikeModel = null;
  defaultImae = '../../assets/images/default.jpg';
  image;
  constructor(private sanitizer: DomSanitizer, public dialog: MatDialog, private blogService: BlogService, private userService: UserService) { }

  ngOnInit(): void {
    const object = 'data:' + this.blog.image.type + ';base64,' + this.blog.image.picByte;
    this.image = this.sanitizer.bypassSecurityTrustUrl(object);
    console.log("from beginig",  this.userService.theUser.username);
    for (const like of this.blog.likes) {
      if (like.user.username ===  this.userService.theUser.username) {
        this.likeIt = true;
        this.like = like;
        console.log("from beginig");
        break;
      }
    }

  }

  openDialog(){
    this.dialogRef = this.dialog.open(BlogDetailComponent, {
      height: '90%',
      width: '80%'
    });
    this.dialogRef.componentInstance.blog = this.blog;
    this.dialogRef.componentInstance.index = this.index;
    this.dialogRef.componentInstance.detailDialogRef = this.dialogRef;
    this.dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  onLike(deleteLike: boolean){
    if(deleteLike){

      if (this.like !== null) {
        this.blogService.deleteLike(this.like.id).subscribe((response) => {
          if (response.status === 200) {
            this.likeIt = false;
            const likeIndex = this.blog.likes.indexOf(this.like);
            this.blogService.blogs[this.index].likes.splice(likeIndex, 1);
            this.blog = this.blogService.blogs[this.index];
            this.like = null;
          }
        });
      }
    } else {
      this.blogService.addLike(this.blog.id).subscribe((response) => {
        if (response.status === 200) {
          this.likeIt = true;

          this.like = response.body as LikeModel;
          this.blogService.blogs[this.index].likes.push(this.like);
          this.blog = this.blogService.blogs[this.index];
        }
      });
    }
  }

  bommarkBlog(){
    this.blogService.bommarkBlog(this.blog.id)
      .subscribe((response) => {
        if (response.status === 200) {
          this.userService.theUser = (response.body as User);


        }
      });

  }
}
