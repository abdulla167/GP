import {Injectable} from "@angular/core";
import {User} from "../models/user.model";

@Injectable({providedIn:"root"})
export class UserService{
  theuser : User = new User("Abdulla", "Elsayed","Abdulla167","male","abdullaelsayed167@yahoo.com"
                              ,null, "01100661997");



}
