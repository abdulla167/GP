import {Component} from "@angular/core";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.model";


@Component({
  selector: 'user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent{
  theUser : User;
  constructor(public userService : UserService) {
    this.theUser = userService.theuser;
  }
}
