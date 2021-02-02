import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
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
}
