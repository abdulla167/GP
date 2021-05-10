import {Component, OnChanges, OnInit, QueryList, SimpleChanges, ViewChild, ViewChildren} from '@angular/core';
import {BlogService} from '../../../services/blog.service';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {BlogModel} from '../../../models/blog.model';

import Quill from 'quill';
import ImageResize from 'quill-image-resize-module';
import {ImageModel} from '../../../models/image.model';
import {EditorChangeContent, QuillEditorComponent} from 'ngx-quill';
Quill.register('modules/imageResize', ImageResize);
@Component({
  selector: 'app-edit-blog',
  templateUrl: './edit-blog.component.html',
  styleUrls: ['./edit-blog.component.css']
})
export class EditBlogComponent implements OnInit {
  html: string;
  content: BlogModel;
  selectedFile: File = null;
  coverImage;
  imageMOdel;
  expanded = false;
  editor_modules = {};
  admin = false;
  index: number;
  checkDEfault: boolean[] = [];
  options = [
    {name: 'Covid-19', value: '1', checked: false},
    {name: 'Pregnancy', value: '2', checked: false},
    {name: 'Labor', value: '3', checked: false},
    {name: 'Deliery', value: '3', checked: false},
    {name: 'baby-feeding', value: '3', checked: false},
    {name: 'breast-feeding', value: '3', checked: false},
    {name: 'vaccination', value: '3', checked: false},
  ];

  constructor(private blogService: BlogService, private activatedRoutes: ActivatedRoute, private sanitizer: DomSanitizer) { }

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

    this.activatedRoutes.params.subscribe(params => {
       this.index = params.index;
       this.content = this.blogService.getBlogByIndex(this.index);

      // this.quill.editorElem.innerHTML = this.content.html;
      if (this.content.image != null) {
        const object = 'data:' + this.content.image.type + ';base64,' + this.content.image.picByte;
        this.coverImage = this.sanitizer.bypassSecurityTrustUrl(object);
        console.log(' there is image');
      }else {
        this.coverImage = null;
      }
      const categList = this.content.categories.split(',');
      for (const option of this.options){
        let isIn = false;
        for (const category of categList) {

          if (option.name === category) {
            isIn = true;
            option.checked = true;
            break;
          }
        }
        this.checkDEfault.push(isIn);
      }
      console.log(this.checkDEfault);
    });
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

  changedEditor(event: EditorChangeContent ) {
    // tslint:disable-next-line:no-console
    if (event.content === undefined) {
      console.log('editor-change', event);
    }
    else  {
      console.log( 'editor-change', event.html) ;
      this.html = event.html;
    }
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
    console.log(categories);
    console.log(this.options);
    if (this.selectedFile !== null) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const array = new Uint8Array(e.target.result as ArrayBuffer),
          // binaryString = String.fromCharCode.apply(null, array);

          image = {
            id: 0,
            name: this.selectedFile.name,
            type: this.selectedFile.type,
            picByte: btoa(String.fromCharCode.apply(null, array))// this.Utf8ArrayToStr(array)
          };

        const blog: BlogModel = new BlogModel(this.content.id, this.admin, this.html, title, description, categories, image, this.content.comments, null, null, this.content.likes);

        this.blogService.updateBlog(blog, this.index);

      };
      reader.readAsArrayBuffer(this.selectedFile);
    }
    else {
      const blog: BlogModel = new BlogModel( this.content.id, this.admin, this.html, title, description, categories, this.content.image, this.content.comments, null, this.content.user, this.content.likes);
      this.blogService.updateBlog(blog, this.index);
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
