import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
  private _imgPath: string;
  constructor(private _firstName:string, private _lastName:string, private _username:string, private _gender:string,
              private _email:string,private _date:Date,private _password:string){}


  get date(): Date {
    return this._date;
  }

  set date(value: Date) {
    this._date = value;
  }

  get imgPath(): string {
    return this._imgPath;
  }

  set imgPath(value: string) {
    this._imgPath = value;
  }

  get firstName(): string {
    return this._firstName;
  }

  set firstName(value: string) {
    this._firstName = value;
  }

  get lastName(): string {
    return this._lastName;
  }

  set lastName(value: string) {
    this._lastName = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get gender(): string {
    return this._gender;
  }

  set gender(value: string) {
    this._gender = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }



  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }
}
