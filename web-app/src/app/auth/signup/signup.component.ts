<<<<<<< HEAD
import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../../models/user.model';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers: []
})
export class SignupComponent implements OnInit {
  datepickerValue: Date;
  defaultGender = 'Male';
  invalidConfirmPassword = false;
  isLoading = false;
  error: string = null;

  constructor(private authService:AuthenticationService, private router: Router) {}



  ngOnInit(): void {
    this.datepickerValue = new Date();
  }

  // tslint:disable-next-line:typedef
  onSubmitForm(form: NgForm){
    if (!form.valid){
      return;
    }else {
      if (form.value.password === form.value.confirmPassword){
        this.invalidConfirmPassword = false;
        const theUser = {firstName : form.value.firstName, lastName : form.value.lastName, username: form.value.username,
                        password: form.value.password, email: form.value.email, phone: form.value.phone};
        this.isLoading = true;
        this.authService.signup(theUser).subscribe(resData => {
          console.log(resData)
          this.router.navigate(['/login'])
        }, resError =>{
          console.log(resError)
          this.error = resError;
        });
        this.isLoading = false;
        form.reset();
      }else {
        this.invalidConfirmPassword = true;
      }
    }
  }
}
=======
import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../../models/user.model';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers: []
})
export class SignupComponent implements OnInit {
  datepickerValue: Date;
  defaultGender = 'Male';
  invalidConfirmPassword = false;
  isLoading = false;
  error: string = null;

  constructor(private authService: AuthenticationService, private router: Router, private userService: UserService) {}


  ngOnInit(): void {
    this.datepickerValue = new Date();
  }

  // tslint:disable-next-line:typedef
  onSubmitForm(form: NgForm){
    if (!form.valid){
      return;
    }else {
      if (form.value.password === form.value.confirmPassword){
        this.invalidConfirmPassword = false;
        const theUser = {firstName : form.value.firstName, lastName : form.value.lastName, username: form.value.username,
                        password: form.value.password, email: form.value.email, phone: form.value.phone};
        this.isLoading = true;
        this.authService.signup(theUser).subscribe(response => {
          console.log(response.body);
          this.userService.theuser = response.body as User;
          this.router.navigate(['/login']);
        }, resError => {
          console.log(resError);
          this.error = resError;
        });
        this.isLoading = false;
        // form.reset();
      }else {
        this.invalidConfirmPassword = true;
      }
    }
  }
}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
