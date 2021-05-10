import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {EditorChangeContent, QuillEditorBase, QuillEditorComponent, QuillModule, QuillViewComponent} from 'ngx-quill';
import {BlogModel} from '../../models/blog.model';
import {BlogService} from '../../services/blog.service';
import {ImageModel} from '../../models/image.model';
import {DomSanitizer} from '@angular/platform-browser';

import Quill from 'quill';
import ImageResize from 'quill-image-resize-module';
Quill.register('modules/imageResize', ImageResize);
@Component({
  selector: 'app-create-blog',
  templateUrl: './create-blog.component.html',
  styleUrls: ['./create-blog.component.css']
})
export class CreateBlogComponent implements OnInit {
  blured = false;
  focused = false;
  content: any;
  selectedFile: File = null;
  coverImage: any = null;
  expanded = false;
  editor_modules = {};
  admin = false;
  options = [
    {name: 'Covid-19', value: '1', checked: false},
    {name: 'Pregnancy', value: '2', checked: false},
    {name: 'Labor', value: '3', checked: false},
    {name: 'Deliery', value: '3', checked: false},
    {name: 'baby-feeding', value: '3', checked: false},
    {name: 'breast-feeding', value: '3', checked: false},
    {name: 'vaccination', value: '3', checked: false},
  ];


  constructor(private blogService: BlogService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {

    this.editor_modules = {
      toolbar: [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],
        [{ header: 1 }, { header: 2 }],               // custom button values
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ script: 'sub' }, { script: 'super' }],      // superscript/subscript
        [{ indent: '-1' }, { indent: '+1' }],          // outdent/indent
        [{ direction: 'rtl' }],                         // text direction
        [{ size: ['small', false, 'large', 'huge'] }],  // custom dropdown
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }],          // dropdown with defaults from theme
        [{ font: [] }],
        [{ align: [] }],
        ['clean'],                                         // remove formatting button
        ['link', 'image', 'video'],                         // link and image, video
        ['emoji']
      ],
      imageResize : ImageResize,
    };
  }
  showCheckboxes() {
    const checkboxes = document.getElementById('checkboxes');
    if (!this.expanded) {
      checkboxes.style.display = 'block';
      this.expanded = true;
    } else {
      checkboxes.style.display = 'none';
      this.expanded = false;
    }

  }

  quillIt( title: string, description: string){
    let categories = '';
    for ( const option of this.options) {
      if (option.checked) {
        categories += option.name  ;
        categories += ',' ;
      }
    }
    categories = categories.slice(0, categories.length - 1 );
    const reader = new FileReader();
    reader.onload = (e) => {
      const array = new Uint8Array(e.target.result as ArrayBuffer),
        // binaryString = String.fromCharCode.apply(null, array);
        image: ImageModel = {
          id: 0,
          name: this.selectedFile.name,
          type: this.selectedFile.type,
          picByte: btoa(String.fromCharCode.apply(null, array))// this.Utf8ArrayToStr(array)
        };
      const blog: BlogModel = new BlogModel(null, this.admin, this.content, title, description, categories, image, null, null, null, null);

      this.blogService.saveBlog(blog);

    };
    reader.readAsArrayBuffer(this.selectedFile);

  }
  created(event: any) {
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
      this.content = event.html;
    }
  }

  focus($event) {
    // tslint:disable-next-line:no-console
    // console.log('focus', $event);
    this.focused = true;
    this.blured = false;
  }
  blur($event) {
    // tslint:disable-next-line:no-console
    console.log('blur', $event);
    this.focused = false;
    this.blured = true;
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile != null && this.selectedFile.type.includes('image')) {
      const reader = new FileReader();
      reader.readAsDataURL(this.selectedFile);
      reader.onload = () => {
        this.coverImage = reader.result;
      };
    }

  }
  checkSelectedFile(){
    if (this.selectedFile == null){
      return false;
    } else {
      return this.selectedFile.type.includes('image');
    }
  }

  anyItemChecked() {
   let anyIsChosen = false;
   for ( const option of this.options) {
       if (option.checked ) {
         anyIsChosen = true;
         break;
       }
    }
   return anyIsChosen;
  }

}
