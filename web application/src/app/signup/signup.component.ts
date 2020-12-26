import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {gender} from '../models/doctor.model';
import {UserService} from '../user.service';
import {User} from '../models/user.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers:[UserService]
})
export class SignupComponent implements OnInit {
  defaultGender = "Male";
  user :User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmitForm(form : NgForm){
    console.log(form)
    if (form.value.password === form.value.confirmPassword){
      this.user.firstName = form.value.firstName;
      this.user.lastName = form.value.lastName;
      this.user.username = form.value.username;
      this.user.gender = form.value.gender;
      this.user.password = form.value.password;
      this.userService.registerUser(this.user)
      form.reset();
    }


  }
}
