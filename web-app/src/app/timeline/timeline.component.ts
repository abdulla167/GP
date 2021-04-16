<<<<<<< HEAD
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
=======
import {Component, HostListener, OnInit} from '@angular/core';
import {Post} from '../models/post.model';
import {PostService} from '../services/post.service';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  posts: Post[];
  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.uploadPosts(1);

    this.postService.postsSubject.subscribe((posts) => {
      this.posts = this.postService.getPosts();
      console.log(this.posts.length);
    });

    this.postService.likedPosts();
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {

    let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
    let max = document.documentElement.scrollHeight;
    if( (max - pos ) <= 1 )   {
      this.postService.uploadPosts(0);
    }
  }
}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
