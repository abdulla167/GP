import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {TokenService} from './Token.service';
import {async, Subject, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {CommentModel} from '../models/comment.model';
import {isIterable} from 'rxjs/internal-compatibility';
import {UserService} from './user.service';
import {BlogModel} from '../models/blog.model';

@Injectable({providedIn: 'root'})
export class BlogService{
  public blogSubject = new Subject<BlogModel[]>();
  blogs: BlogModel[] ;
  blogUpdateEvent: EventSource;

  constructor(private http: HttpClient, private tokenService: TokenService, private userService: UserService) {
  }
  getBlogs() {
    return this.blogs.slice();
  }
  addBlog(blog: BlogModel) {
    this.blogs.push(blog);
  }
  getBlogByIndex(index: number) {
    return this.blogs[index];
  }

  saveBlog(blog: BlogModel ){
    console.log(this.tokenService.getToken());
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    // tslint:disable-next-line:max-line-length
    return this.http.post('http://localhost:8080/blog/save',
      JSON.parse(JSON.stringify(blog)) ,
      {observe: 'response', headers })
      .subscribe((response) => {
        if (response.status === 200) {
          // this.addPost(new Post(stringify(post.get('text')), null, null, null));
          console.log('Image uploaded successfully');
        } else {
          console.log('Image not uploaded successfully');
        }
      });
  }

  BlogsCount() {
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.get('\'http://localhost:8080/blog/count' , {observe: 'response', headers});
  }
  uploadBlogs(cycle: number){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    this.http.get('http://localhost:8080/blog/get/' + cycle, {observe: 'response', headers}).subscribe(
      (response) => {
        const blogs: BlogModel[] = (response.body as BlogModel[]);
        console.log('service', response.body);
        if ( isIterable(blogs)){
          if (cycle === 0)
          {
            for (const blog of blogs) {
              this.addBlog(blog);
            }
          }else
          {
            this.blogs = blogs;
          }
          this.blogSubject.next(this.getBlogs());
        }

      }
    );

  }
  saveComment(blohgId: number, comment: CommentModel){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    console.log('http://localhost:8080/comment' + blohgId);
    return this.http.post('http://localhost:8080/comment/' + blohgId, comment, { observe: 'response', headers});

  }

  deleteBlog(id: number){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.post('http://localhost:8080/blog/delete/' + id, null, {observe: 'response', headers}).subscribe((response) => {
      if (response.status === 200){
        console.log(response.body);
      }
    });
  }
  saveCommentToComment(commentId: number, comment: CommentModel){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.post('http://localhost:8080/commentToComment/' + commentId, comment, { observe: 'response', headers});

  }
  addLike(blogId: number){

    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };

    return this.http.post('http://localhost:8080/like/' + blogId, null, {observe: 'response', headers});
  }
  deleteLike(likeId: number){

    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };

    return this.http.post('http://localhost:8080/like/delete/' + likeId, null, {observe: 'response', headers});
  }

  bommarkBlog(blogId: number) {
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.post('http://localhost:8080/blog/bommark/'+blogId, null, {observe: 'response', headers});
  }

  bommarks() {
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.post('http://localhost:8080/blog/bommark', null, {observe: 'response', headers});
  }

  likedBlogs(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    return this.http.get('http://localhost:8080/liked_blogs', {observe: 'response', headers}).subscribe((response) => {
      if (response.status === 200){
        console.log(response.body);
      }
    });
  }

  getUpdates(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    this.blogUpdateEvent = new EventSource('http://localhost:8080/blog/updates');
    // tslint:disable-next-line:typedef only-arrow-functions
    this.blogUpdateEvent.onmessage = function(e)
    {
      console.log('Message Received');
      console.log(JSON.parse(e.data));
    };
  }

  updateBlog(theBlog: BlogModel, index: number) {
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    console.log(theBlog);
    this.http.post('http://localhost:8080/blog/update/' + theBlog.id, theBlog, {observe: 'response', headers}).subscribe((response) => {
      if (response.status === 200){
        this.blogs[index] = (response.body as BlogModel);
      }
    });
  }

  private handleError(errResp: HttpErrorResponse) {
    if (errResp.status === 200){
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
