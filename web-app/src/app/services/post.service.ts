import {Post} from '../models/post.model';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {toString} from '@ng-bootstrap/ng-bootstrap/util/util';
import {stringify} from 'querystring';
import {User} from '../models/user.model';
import {TokenService} from './Token.service';
import {async, Subject, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {getClassName} from 'codelyzer/util/utils';
import {CommentModel} from '../models/comment.model';
import {isIterable} from 'rxjs/internal-compatibility';
import {LikeModel} from '../models/like.model';
import {UserService} from './user.service';
// import {type} from 'os';

@Injectable({providedIn: 'root'})
export class PostService{
  public postsSubject = new Subject<Post[]>();
  posts: Post[] ;

  constructor(private http: HttpClient, private tokenService: TokenService, private userService: UserService) {
  }
  getPosts() {
    return this.posts.slice();
  }
  addPost(post: Post) {
    this.posts.push(post);
  }
  getPostByIndex(index: number) {
    return this.posts[index];
  }

  savePost(image: FormData, post: Post ){
    console.log(this.tokenService.getToken());
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    return this.http.post('http://localhost:8080/post/save',  JSON.parse(JSON.stringify(post)) , {observe: 'response', headers: headers }).pipe(catchError(this.handleError))
      .subscribe((response) => {
      if (response.status === 200) {
        // this.addPost(new Post(stringify(post.get('text')), null, null, null));
        console.log('Image uploaded successfully');
      } else {
        console.log('Image not uploaded successfully');
      }
    });
  }

  uploadPosts(cycle: number){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
     this.http.get('http://localhost:8080/post/get/' + cycle, {observe: 'response', headers}).subscribe(
      (response) => {
        const posts: Post[] = (response.body as Post[]);
        if( isIterable(posts)){
          if (cycle === 0)
          {
            for (const post of posts) {
              this.addPost(post);
            }
          }else
          {
            this.posts = posts;
          }
          this.postsSubject.next(this.getPosts());
        }

      }
    );

  }
  saveComment(postId: number, comment: CommentModel){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    console.log('http://localhost:8080/comment' + postId);
     return this.http.post('http://localhost:8080/comment/' + postId, comment,{ observe: 'response',headers: headers});

  }
  saveCommentToComment(commentId: number, comment: CommentModel){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
     return this.http.post('http://localhost:8080/commentToComment/' + commentId, comment,{ observe: 'response',headers: headers});

  }
  addLike(postId: number, postIndex: number){

    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };

      return this.http.post('http://localhost:8080/like/' + postId, null, {observe: 'response', headers});
  }

  likedPosts(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.get('http://localhost:8080/liked_posts',{observe: 'response', headers}).subscribe((response) => {
      if(response.status === 200){
        console.log(response.body);
      }
    });
  }
  getJsonFromFormData(formData: FormData){
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
    const json = JSON.stringify(object);
    console.log(json);
    return json;
  }
  private handleError(errResp: HttpErrorResponse) {
    if (errResp.status == 200){
      return ;
    }else {
      let errorMessage = 'An unknown error message';
      if (!errResp.error || !errResp.error.error) {
        return throwError(errorMessage);
      }
      switch (errResp.error.message) {
        case 'EMAIL_EXISTS':
          errorMessage = 'This email is already exists';
          break;
        case 'INVALID_USERNAME_PASSWORD':
          errorMessage = 'This is invalid username or password';
        default:
          errorMessage = 'Something wrong happened';
      }
      return throwError(errorMessage);
    }
  }
}
