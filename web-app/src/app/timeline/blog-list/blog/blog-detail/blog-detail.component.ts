import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../../../models/blog.model';
import {BlogService} from '../../../../services/Blog.service';
import {ActivatedRoute} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {CreateBlogComponent} from '../../../create-blog/create-blog.component';

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrls: ['./blog-detail.component.css']
})
export class BlogDetailComponent implements OnInit {
  blog: BlogModel;
  @Input() blog_: BlogModel = null;
  @Input() newTap: boolean = false;
  detailDialogRef;
  createDialogRef;
  index: number;
  defaultImae = '../../assets/images/default.jpg';

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    if (this.blog_ !== null){
      this.blog = this.blog_;
    }
  }
  closeDialog() {
    this.detailDialogRef.close();
  }

  openDialogCreate(){
    this.detailDialogRef.close();
    this.createDialogRef = this.dialog.open(CreateBlogComponent);
    this.createDialogRef.componentInstance.blogEdit = this.blog;
    this.createDialogRef.componentInstance.editBlog = true;
    this.createDialogRef.componentInstance.dialogRef = this.createDialogRef;
    this.createDialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }


}
