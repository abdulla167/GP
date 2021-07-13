import { Component, OnInit } from '@angular/core';
import {absRound} from 'ngx-bootstrap/chronos/utils/abs-round';
import {PageEvent} from '@angular/material/paginator';
import {BlogService} from '../../services/Blog.service';
import {BlogModel} from '../../models/blog.model';

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  breakpoint;
  pageEvent: PageEvent;
  totalLength: number = 0;
  pageIndex: number = 0;
  blogs: BlogModel[];
  constructor(private blogService: BlogService) { }

  ngOnInit() {
    // this.blogService.uploadBlogs(1)
    // this.blogService.BlogsCount().subscribe(
    //   (response) => {
    //     if (response.status === 200) {
    //       this.totalLength = response.body['count'];
    //       this.blogService.uploadBlogs(1);
    //     }
    //   }
    // );
    this.blogService.blogSubject.subscribe((blogs) => {
      this.blogs = this.blogService.getBlogs();
      console.log(this.blogs.length);
    });
    this.blogService.uploadBlogs(1);
    this.breakpoint = (window.innerWidth - 150) > 400 ? (( (window.innerWidth - 150) / 370) - ((window.innerWidth - 150) % 370) / 370) : 1;
  }

  onResize(event) {
    this.breakpoint = (event.target.innerWidth - 150) > 400 ? (((event.target.innerWidth - 150) / 370) - ((event.target.innerWidth - 150) % 370) / 370) : 1;
  }

  getServerData(event: PageEvent){
    // event.pageIndex
    this.blogService.uploadBlogs(0);
    return event;
  }
}
