import { Component, OnInit } from '@angular/core';
import {BlogService} from '../services/Blog.service';
import {BlogModel} from '../models/blog.model';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {
  constructor( ) { }

  ngOnInit(): void {

  }

}
