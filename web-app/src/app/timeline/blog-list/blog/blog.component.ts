import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../../models/blog.model';
import {DomSanitizer} from '@angular/platform-browser';
import {CreateBlogComponent} from '../../create-blog/create-blog.component';
import {MatDialog} from '@angular/material/dialog';
import {BlogDetailComponent} from './blog-detail/blog-detail.component';
import {BlogService} from '../../../services/Blog.service';
import {LikeModel} from '../../../models/like.model';
import {UserService} from '../../../services/user.service';
import { isThisSecond } from 'date-fns';
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
  likeIt: boolean = false;
  likeIndex: number;
  defaultImae = '../../assets/images/default.jpg';
  image;
  constructor(private sanitizer: DomSanitizer, public dialog: MatDialog, private blogService: BlogService, private userService: UserService) { }

  ngOnInit(): void {
    const object = 'data:' + this.blog.image.type + ';base64,' + this.blog.image.picByte;
    this.image = this.sanitizer.bypassSecurityTrustUrl(object);
    for (const like of this.blog.likes) {
      if (like.user.username === this.userService.theuser.username) {
        this.likeIt = true;
        this.likeIndex = this.blog.likes.indexOf(like);
        break;
      }
    }
  }

  openDialog(){
    this.dialogRef = this.dialog.open(BlogDetailComponent);
    this.dialogRef.componentInstance.blog = this.blog;
    this.dialogRef.componentInstance.index = this.index;
    this.dialogRef.componentInstance.detailDialogRef = this.dialogRef;
    this.dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  like(deleteLike: boolean){
    if(deleteLike){
      const likeId = this.blog.likes[this.likeIndex].id;
      this.blogService.deleteLike(likeId).subscribe((response) =>{
        if (response.status === 200) {
          this.likeIt = false;
          this.blogService.blogs[this.index].likes.splice(this.likeIndex,1);
        }
      });
    } else {
      this.blogService.addLike(this.blog.id).subscribe((response) => {
        if (response.status === 200) {
          this.likeIt = true;
          this.likeIndex = this.blog.likes.length;
          this.blogService.blogs[this.index].likes.push( response.body as LikeModel);
        }
      });
    }
  }

  bommarkBlog(){
    this.blogService.bommarkBlog(this.blog.id)
      .subscribe((response) => {
        if (response.status === 200) {
          this.userService.theuser = (response.body as User);

        }
      });

  }
}
