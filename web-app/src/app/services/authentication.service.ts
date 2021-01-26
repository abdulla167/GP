import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user.model";
import {Subject} from "rxjs";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";


@Injectable({providedIn:"root"})
export class AuthenticationService{
  public user = new Subject<User>();

  constructor(private http: HttpClient) {}

  /* [Description] : METHOD USED TO SEND POST HTTP REQUEST TO THE API TO SIGN UP NEW USER
   *                 AND RETURN OBSERVABLE WHICH YOU SHOULD SUBSCRIBE TO GET TH RESPONSE.
   * [Parameters] : User OBJECT
   * [Returns] : Observable OBJECT
   */
  signup(newUser : User){
    return this.http.post("localhost:8080/signupNewUser", newUser).pipe(catchError(respError => {
        switch (respError.error.error.message){

        }
      }
    ))
  }

  signin(username:string, password:string){
    return this.http.post("localhost:8080/signin", {username:username,password:password}).pipe(catchError(respError => {
      switch (respError.error.error.message){

      }
    }))
  }

  saveToken(token) {
    let expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    console.log('Obtained Access token');
    window.location.href = 'http://localhost:8089';
  }
}
