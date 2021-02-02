import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user.model';
import {Observable, Subject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {throwError} from 'rxjs';
import { Cookie } from 'ng2-cookies/ng2-cookies';


@Injectable({providedIn: 'root'})
export class AuthenticationService{
  public user = new Subject<User>();

  constructor(private http: HttpClient) {}

  /* [Description] : METHOD USED TO SEND POST HTTP REQUEST TO THE API TO SIGN UP NEW USER
   *                 AND RETURN OBSERVABLE WHICH YOU SHOULD SUBSCRIBE TO GET TH RESPONSE.
   * [Parameters] : User OBJECT
   * [Returns] : Observable OBJECT
   */
  // tslint:disable-next-line:typedef
  signup(newUser: User){
    return this.http.post('http://localhost:8080/register/newUser', JSON.parse(JSON.stringify(newUser))).pipe(catchError(respError => {
        // switch (respError.error.error.message){
        //
        // }
      console.log(JSON.stringify(newUser));
      return Observable.throw(respError.error.error.message);
      }
    ));
  }

  signin(username: string, password: string){
    return this.http.post('http://localhost:8080/signin', {username, password}).pipe(tap( // Log the result or error
      data => console.log(data),
      error => console.log(error)
    ));
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set('access_token', token.access_token, expireDate);
    console.log('Obtained Access token');
    window.location.href = 'http://localhost:8089';
  }
}
