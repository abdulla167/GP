import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
// <<<<<<< HEAD
//
//
//   constructor(private _userId: string,  private _token: string,
//               private _refreshToken : string,  private _tokeExpirationDate: Date){}
//
//
//   get userId(): string {
//     return this._userId;
//   }
//
//   set userId(value: string) {
//     this._userId = value;
//   }
//
//   get refreshToken(): string {
//     return this._refreshToken;
//   }
//
//   set refreshToken(value: string) {
//     this._refreshToken = value;
//   }
//
//   get tokeExpirationDate(): Date {
//     return this._tokeExpirationDate;
//   }
//
//   set tokeExpirationDate(value: Date) {
//     this._tokeExpirationDate = value;
//   }
//
//
//   get token(){
//     if (!this._tokeExpirationDate || new Date() > this._tokeExpirationDate){
//       return null;
//     }
//     return this.token;
//   }
//
//
//
// =======
  private _imgPath: string;
  constructor(private firstName:string, private lastName:string, private username:string, private gender:string,
              private email:string, private birthOfDate:Date, private password:string){}


  get getBirthOfDate(): Date {
    return this.birthOfDate;
  }

  set setBirthOfDate(value: Date) {
    this.birthOfDate = value;
  }

  get imgPath(): string {
    return this._imgPath;
  }

  set imgPath(value: string) {
    this._imgPath = value;
  }

  get getFirstName(): string {
    return this.firstName;
  }

  set setFirstName(value: string) {
    this.firstName = value;
  }

  get getLastName(): string {
    return this.lastName;
  }

  set setLastName(value: string) {
    this.lastName = value;
  }

  get getUsername(): string {
    return this.username;
  }

  set setUsername(value: string) {
    this.username = value;
  }

  get getGender(): string {
    return this.gender;
  }

  set setGender(value: string) {
    this.gender = value;
  }

  get getEmail(): string {
    return this.email;
  }

  set setEmail(value: string) {
    this.email = value;
  }



  get getPassword(): string {
    return this.password;
  }

  set setPassword(value: string) {
    this.password = value;
  }
// >>>>>>> 3e0b7e17c283d87717f918ca12a7a2c446e9478f
}
