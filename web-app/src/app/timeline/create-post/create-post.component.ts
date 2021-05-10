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

  message: string;
  imageName: any;
  image: FormData ;
  logoImg = '../../assets/images/logo.svg';
  constructor(private postService: PostService) {
  }

  ngOnInit(): void {
  }

  // tslint:disable-next-line:typedef
  createPost(text: HTMLTextAreaElement) {

    console.log('kkk');
    this.image = new FormData();
    const reader = new FileReader();
    reader.onload = (e) => {
      const array = new Uint8Array(e.target.result as ArrayBuffer),
        // binaryString = String.fromCharCode.apply(null, array);

      post = { id: 0, text: text.value, image: {
      id: 0,
      name: this.selectedFile.name,
      type: this.selectedFile.type,
      picByte: btoa(String.fromCharCode.apply(null, array))// this.Utf8ArrayToStr(array)
    }, comments: null, date: null, user: null, likes: null};

      if (this.selectedFile != null){
        this.image.append('imageFile', this.selectedFile, this.selectedFile.name);
      }
      this.image.append('text', text.value);

      this.postService.savePost(this.image, post);
      console.log(post);

  };
    reader.readAsArrayBuffer(this.selectedFile);

  }

  selectImage() {
    this.selectingImage = true;
  }


  onFileChanged(event) {
    this.selectedFile = event.target.files[0];

  }
  morePosts(){
    this.postService.uploadPosts(0);
  }

}
