import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../../models/blog.model';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {
  @Input() blog: BlogModel;
  defaultImae = '../../assets/images/default.jpg';
  image;
  constructor(private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    const object = 'data:' + this.blog.image.type + ';base64,' + this.blog.image.picByte;
    this.image = this.sanitizer.bypassSecurityTrustUrl(object);
  }

}
