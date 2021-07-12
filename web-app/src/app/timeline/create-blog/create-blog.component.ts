import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, NgForm} from '@angular/forms';
import {BlogService} from '../../services/Blog.service';
import {BlogModel} from '../../models/blog.model';
import {ImageModel} from '../../models/image.model';

@Component({
  selector: 'app-create-blog',
  templateUrl: './create-blog.component.html',
  styleUrls: ['./create-blog.component.css']
})
export class CreateBlogComponent implements OnInit {
  @ViewChild('fileInput') fileInput;
  file: File | null = null;
  dialogWidth;
  categories = new FormControl();
  categoryList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];
  BlogContent: string = null;
  constructor(private blogService: BlogService) { }

  ngOnInit(): void {
  }
  onResize(event){
    console.log(event.target.innerWidth);
  }

  onClickFileInputButton(): void {
    this.fileInput.nativeElement.click();
  }

  onChangeFileInput(): void {
    const files: { [key: string]: File } = this.fileInput.nativeElement.files;
    this.file = files[0];
  }

  submit(title: string, description: string){

    let catgs = '';
    if (this.categories.value != null){
      for (const category of this.categories.value){
          catgs += category;
          catgs += ',';
      }
      catgs = catgs.slice(0, catgs.length - 1);
    }
    const reader = new FileReader();
    reader.onload = (e) => {
      const array = new Uint8Array(e.target.result as ArrayBuffer),
        // binaryString = String.fromCharCode.apply(null, array);
        image: ImageModel = {
          id: 0,
          name: this.file.name,
          type: this.file.type,
          picByte: btoa(String.fromCharCode.apply(null, array))// this.Utf8ArrayToStr(array)
        };
      const blog: BlogModel = new BlogModel(null, null, this.BlogContent, title, description, catgs, image, null, null, null, null);

      this.blogService.saveBlog(blog);

    };
    reader.readAsArrayBuffer(this.file);
  }
}
