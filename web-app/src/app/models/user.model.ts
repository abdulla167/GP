import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{


  constructor(private _userId: string,  private _token: string,
              private _refreshToken : string,  private _tokeExpirationDate: Date){}


  get userId(): string {
    return this._userId;
  }

  set userId(value: string) {
    this._userId = value;
  }

  get refreshToken(): string {
    return this._refreshToken;
  }

  set refreshToken(value: string) {
    this._refreshToken = value;
  }

  get tokeExpirationDate(): Date {
    return this._tokeExpirationDate;
  }

  set tokeExpirationDate(value: Date) {
    this._tokeExpirationDate = value;
  }


  get token(){
    if (!this._tokeExpirationDate || new Date() > this._tokeExpirationDate){
      return null;
    }
    return this.token;
  }


// constructor(private _firstName:string, private _lastName:string,private _password:string, private _username:string, private _gender:string,
  //             private _email:string,private _date:Date){}
  //
  //
  // get date(): Date {
  //   return this._date;
  // }
  //
  // set date(value: Date) {
  //   this._date = value;
  // }
  //
  // get imgPath(): string {
  //   return this._imgPath;
  // }
  //
  // set imgPath(value: string) {
  //   this._imgPath = value;
  // }
  //
  // get firstName(): string {
  //   return this._firstName;
  // }
  //
  // set firstName(value: string) {
  //   this._firstName = value;
  // }
  //
  // get lastName(): string {
  //   return this._lastName;
  // }
  //
  // set lastName(value: string) {
  //   this._lastName = value;
  // }
  //
  // get username(): string {
  //   return this._username;
  // }
  //
  // set username(value: string) {
  //   this._username = value;
  // }
  //
  // get gender(): string {
  //   return this._gender;
  // }
  //
  // set gender(value: string) {
  //   this._gender = value;
  // }
  //
  // get email(): string {
  //   return this._email;
  // }
  //
  // set email(value: string) {
  //   this._email = value;
  // }


  // get password(): string {
  //   return this._password;
  // }
  //
  // set password(value: string) {
  //   this._password = value;
  // }
}
