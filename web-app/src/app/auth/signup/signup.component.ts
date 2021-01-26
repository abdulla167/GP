import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../../models/user.model';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers:[]
})
export class SignupComponent implements OnInit {
  datepickerValue: Date;
  defaultGender = "Male";
  invalidConfirmPassword = false;
  isLoading = false;
  error : string = null;



  constructor(private authService:AuthenticationService) { }

  ngOnInit(): void {
    this.datepickerValue = new Date();

  }

  onSubmitForm(form : NgForm){
    if (!form.valid){
      return;
    }else {
      if (form.value.password === form.value.confirmPassword){
        this.invalidConfirmPassword = false;
        let theUser = new User(form.value.firstName, form.value.lastName, form.value.username, form.value.gender,form.value.email,form.value.datepicker ,form.value.password);
        this.isLoading = true;
        this.authService.signup(theUser).subscribe(resData => {
        }, error =>{
          this.error = "Something wrong happened";
        });
        this.isLoading = false;
        form.reset();
      }else {
        this.invalidConfirmPassword= true;
      }
    }
  }
}
