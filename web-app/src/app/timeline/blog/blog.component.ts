import {Component, Input, OnInit} from '@angular/core';
import {BlogModel} from '../../models/blog.model';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {
  @Input() index: number;
  @Input() content: BlogModel;
  image;
  profileImage;
  defaultImae = '../../assets/images/default.jpg';
  constructor(private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    if (this.content.image != null) {
      const object = 'data:' + this.content.image.type + ';base64,' + this.content.image.picByte;
      this.image = this.sanitizer.bypassSecurityTrustUrl(object);
    }else {
      this.image = this.defaultImae;
    }
    if (this.content.user.profileImg != null) {
      const object = 'data:' + this.content.user.profileImg.type + ';base64,' + this.content.user.profileImg.picByte;
      this.profileImage = this.sanitizer.bypassSecurityTrustUrl(object);
    }else {
      this.profileImage = this.defaultImae;
    }
  }

}
