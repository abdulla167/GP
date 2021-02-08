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
    )
      .subscribe(( response) => {
        if (response.status === 200 ){
          // this.tokenService.saveToken(JSON.parse(JSON.stringify(response.body)).get('access_token'));
          this.tokenService.saveToken( response.body['access_token']);
        }
      });
  }

  private handleError(errResp: HttpErrorResponse) {
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
