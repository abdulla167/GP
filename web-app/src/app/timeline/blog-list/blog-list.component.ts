import { Component, OnInit } from '@angular/core';
import {absRound} from 'ngx-bootstrap/chronos/utils/abs-round';
import {PageEvent} from '@angular/material/paginator';
import {BlogService} from '../../services/Blog.service';

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  breakpoint;
  pageEvent: PageEvent;
  pageIndex:number = 0;
  blogs = [1, 2, 3, 4, 5, 6, 7];
  constructor(private blogService: BlogService) { }

  ngOnInit() {
    // this.blogService.uploadBlogs(1)
    this.blogService.BlogsCount().subscribe()
    this.breakpoint = (window.innerWidth - 150) > 400 ? (( (window.innerWidth - 150) / 370) - ((window.innerWidth - 150) % 370) / 370) : 1;
  }

  onResize(event) {
    this.breakpoint = (event.target.innerWidth - 150) > 400 ? (((event.target.innerWidth - 150) / 370) - ((event.target.innerWidth - 150) % 370) / 370) : 1;
  }

  getServerData(event: PageEvent){
    console.log(event.pageIndex);
    return event;
  }
}
