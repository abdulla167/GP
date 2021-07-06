import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {User} from '../models/user.model';
import {Subject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {TokenService} from './Token.service';




@Injectable({providedIn: 'root'})
export class AuthenticationService{
  public user = new Subject<User>();



  constructor(private http: HttpClient, private tokenService: TokenService) {}

  /* [Description] : METHOD USED TO SEND POST HTTP REQUEST TO THE API TO SIGN UP NEW USER
   *                 AND RETURN OBSERVABLE WHICH YOU SHOULD SUBSCRIBE TO GET TH RESPONSE.
   * [Parameters] : User OBJECT
   * [Returns] : Observable OBJECT
   */
  signup(newUser: { firstName: string; lastName: string; password: string; phone: string; email: string; username: string }){
    return this.http.post('http://localhost:8080/register/newUser', newUser).pipe(catchError(this.handleError));
  }

  login(username: string, password: string){
    const headers = {
      'Authorization': 'Basic ' + btoa('mothercare-webapp:6969'),
      'Content-type': 'application/x-www-form-urlencoded'
    };
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');
    return this.http.post('http://localhost:8080/oauth/token', body, {observe: 'response', headers}).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(errResp: HttpErrorResponse) {
    console.log(errResp)
    let errorMessage = 'Unknown error';
    switch (errResp.error.error){
      case ("invalid_grant"):
        errorMessage = "Invalid username or password";
        break;
      case ("user_exist"):
        errorMessage =errResp.error.error_description;
        break;
    }
    return throwError(errorMessage);
  }


}
