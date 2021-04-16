import { Component, OnInit } from '@angular/core';
import {PostModel} from '../models/post.model';
import {PostService} from '../services/post.service';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {
  posts: PostModel[];
  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.postsSubject.subscribe((posts) => {
      this.posts = this.postService.getPosts();
      console.log(this.posts.length);
    });
    this.postService.uploadPosts();
  }

}
