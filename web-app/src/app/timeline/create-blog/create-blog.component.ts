import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, NgForm} from '@angular/forms';
import {BlogService} from '../../services/Blog.service';
import {BlogModel} from '../../models/blog.model';
import {ImageModel} from '../../models/image.model';
import {MatSnackBar} from '@angular/material/snack-bar';

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
  categoryList: string[] = ['COVID-19', 'Labor', 'Delivery', 'Breast Feeding', 'Baby', 'Exercise'];
  BlogContent: string = null;
  blogEdit: BlogModel;
  dialogRef;
  editBlog = false;
  blogIndex: number;


  constructor(private blogService: BlogService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    if (this.editBlog ) {
      this.blogIndex = this.blogService.getBlogs().indexOf(this.blogEdit);
      this.categories.setValue(this.blogEdit.categories.split(','));
    } else {
      this.blogEdit = new BlogModel(null, null, null, null, null,
        null, null, null, null, null, null);
      console.log("kimooo");
    }
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
    console.log(this.blogEdit.title, this.blogEdit.description, this.blogEdit.categories);
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
    this.blogEdit.categories = catgs;
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
      this.blogEdit.image = image;

      this.blogService.saveBlog(this.blogEdit).subscribe((response) => {
        this.activateSnack(response.status);
      });

    };
    reader.readAsArrayBuffer(this.file);
  }
  saveOrUpdateBlog(){
    if(this.editBlog){
      return this.blogService.updateBlog(this.blogEdit).subscribe((response) => {
        if (response.status === 200){
          this.blogService.blogs[this.blogIndex] = (response.body as BlogModel);
          this.dialogRef.close();
        }
      });
    } else {
      return this.blogService.saveBlog(this.blogEdit).subscribe((response) => {
        this.activateSnack(response.status);
      });
    }
  }
  activateSnack(status){
    if (status === 200) {
      this.dialogRef.close();
      this.snackBar.open('blog created suceesfully!!', 'ok', {
        duration: 2000,
        panelClass: ['green-snack']
      } );
    } else {
      this.snackBar.open('Failure! please try again', 'ok', {
        duration: 2000,
        panelClass: ['red-snack']
      } );
    }
  }
}
