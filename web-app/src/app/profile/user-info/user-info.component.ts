import {Component} from "@angular/core";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.model";


@Component({
  selector: 'user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent{
  theUser : User = new User("ahmed", "mohamed", "mido", "heis123456789", "male", "asfgzffdfgg", null, "01120090244");
  constructor(public userService : UserService) {
  }
}
