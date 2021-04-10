import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
  public profileImg: any;
  constructor(public id: number, public firstName:string, public lastName:string, public username:string, public gender:string,
              public email:string, public birthOfDate:Date, public phone :string){}
}
