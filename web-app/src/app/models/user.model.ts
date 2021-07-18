import {Injectable} from "@angular/core";
import {UserInfoModel} from "./user-info.model";


@Injectable({providedIn:"root"})
export class User{
  public profileImg: any;
  public userInfo : UserInfoModel = new UserInfoModel();
  public additionalInfo : boolean;
  constructor(public firstName:string, public lastName:string, public username:string, public password: string, public gender:string,
              public email:string, public birthOfDate:Date, public phone :string){}
}
