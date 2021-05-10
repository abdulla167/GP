import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {Post} from '../models/post.model';
import {PostService} from '../services/post.service';
import {Subject} from 'rxjs';
import {EditorChangeContent, EditorChangeSelection, QuillEditorBase, QuillEditorComponent} from 'ngx-quill';
import {BlogService} from '../services/blog.service';
import {BlogModel} from '../models/blog.model';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {
  blured = false;
  focused = false;
  // posts: Post[];
  blogs: BlogModel[];
  constructor(private postService: PostService, private blogService: BlogService) { }

  ngOnInit(): void {

    this.blogService.uploadBlogs(1);
    this.blogService.blogSubject.subscribe((blogs) => {
      this.blogs = blogs;
    });
    // this.blogService.getUpdates();
  }

  quillIt(element: QuillEditorComponent){
    console.log(element);

  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {

    let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
    let max = document.documentElement.scrollHeight;
    if ((max - pos ) <= 1)   {
      this.blogService.uploadBlogs(0);

    }
  }
  created(event) {
    // tslint:disable-next-line:no-console
    console.log('editor-created', event.content);
  }

  changedEditor(event: EditorChangeContent ) {
    // tslint:disable-next-line:no-console
    if (event.content === undefined) {
      console.log('editor-change', event);
    }
    else  {
      console.log( 'editor-change', event.html) ;
    }
  }

  focus($event) {
    // tslint:disable-next-line:no-console
    console.log('focus', $event);
    this.focused = true;
    this.blured = false;
  }
  blur($event) {
    // tslint:disable-next-line:no-console
    console.log('blur', $event);
    this.focused = false;
    this.blured = true;
  }
}
