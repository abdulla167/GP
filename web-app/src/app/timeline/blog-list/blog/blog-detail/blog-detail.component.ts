import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../../../models/blog.model';
import {BlogService} from '../../../../services/Blog.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrls: ['./blog-detail.component.css']
})
export class BlogDetailComponent implements OnInit {
  blog: BlogModel;
  @Input() blog_: BlogModel = null;
  @Input() newTap: boolean = false;
  dialogRef;
  index: number;
  defaultImae = '../../assets/images/default.jpg';

  constructor() { }

  ngOnInit(): void {
    if (this.blog_ !== null){
      this.blog = this.blog_;
    }
  }
  closeDialog() {
    this.dialogRef.close();
  }


}
