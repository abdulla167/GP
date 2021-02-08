import { Component, OnInit } from '@angular/core';
import {PostService} from '../../services/post.service';
import {ImageModel} from '../../models/image.model';
import {Post} from '../../models/post.model';
import {stringify} from 'querystring';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  // post: FormData = new FormData();
  arrayBuffer: ArrayBuffer;
  selectingImage: boolean;
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
  image: FormData ;
  post: Post;

  constructor(private postService: PostService) {
  }

  ngOnInit(): void {
  }

  createPost(text: HTMLTextAreaElement) {
    // const post: FormData = new FormData();

    // this.post = new Post(text.value, null, null, null);
    // post.append('text', text.value );
    // post.append('image', this.image, this.imageName);

    this.image = new FormData();
    const reader = new FileReader();
    reader.onload = (e) => {
      const array = new Uint8Array(e.target.result as ArrayBuffer),
        binaryString = String.fromCharCode.apply(null, array);
      // console.log(this.Utf8ArrayToStr(array));

     this.post = { text: text.value, image: {
      id: 0,
      name: this.selectedFile.name,
      type: this.selectedFile.type,
      picByte: btoa(String.fromCharCode.apply(null, array))//this.Utf8ArrayToStr(array)
    }, date: null, user: null};
      if (this.selectedFile != null){
        this.image.append('imageFile', this.selectedFile, this.selectedFile.name);
      }
      this.image.append('text', text.value);

      this.postService.savePost(this.image, this.post);

  };
    reader.readAsArrayBuffer(this.selectedFile);


  }
  getObjectFromFormData(formData: FormData){
    const object = {};
    formData.forEach((value, key) => {
      // Reflect.has in favor of: object.hasOwnProperty(key)
      if (!Reflect.has(object, key)){
        object[key] = value;
        return;
      }
      if (!Array.isArray(object[key])){
        object[key] = [object[key]];
      }
      object[key].push(value);
    });
    console.log(object);
    return object;
  }

  selectImage() {
    this.selectingImage = true;
  }

  getImage() {
    this.postService.uploadPosts();
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];

  }

  onUpload() {

    this.image = new FormData();
    // this.image.append('imageFile', null);
    // console.log(JSON.stringify(this.getObjectFromFormData(this.image)));
    // console.log(this.image.getAll('imageFile'));
}

Utf8ArrayToStr(array) {
    let out, i, len, c;
    let char2, char3;

    out = '';
    len = array.length;
    i = 0;
    while (i < len) {
      c = array[i++];
      switch (c >> 4)
      {
        case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
        // 0xxxxxxx
        out += String.fromCharCode(c);
        break;
        case 12: case 13:
        // 110x xxxx   10xx xxxx
        char2 = array[i++];
        out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
        break;
        case 14:
          // 1110 xxxx  10xx xxxx  10xx xxxx
          char2 = array[i++];
          char3 = array[i++];
          out += String.fromCharCode(((c & 0x0F) << 12) |
            ((char2 & 0x3F) << 6) |
            ((char3 & 0x3F) << 0));
          break;
      }
    }

    return out;
  }
}
