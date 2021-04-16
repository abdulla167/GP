<<<<<<< HEAD
import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
  public profileImg: any;
  constructor(public firstName:string, public lastName:string, public username:string, public gender:string,
              public email:string, public birthOfDate:Date, public phone :string){}
}
=======
import {Injectable} from "@angular/core";

@Injectable({providedIn:"root"})
export class User{
  public profileImg: any;
  constructor(public id: number, public firstName:string, public lastName:string, public username:string, public gender:string,
              public email:string, public birthOfDate:Date, public phone :string){}
}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
