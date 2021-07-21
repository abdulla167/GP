import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class TokenService{

  tokenSubject : Subject<string>;

  constructor(private _accessToken : string = null, private _refreshToken : string = null, private _expiresIn : string =null) {
    this.tokenSubject = new Subject<string>();
  }

  setAccessToken(token: string){
    this._accessToken = token;
    this.tokenSubject.next(token);
  }

  getAccessToken(){
    return this._refreshToken;
  }

  setRefreshToken(token : string){
    this._refreshToken = token;
  }

  getRefreshToken(){
    return this._refreshToken;
  }

}
