import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../../models/user.model';
import {AuthenticationService} from '../../services/authentication.service';

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

  constructor(private authService: AuthenticationService) {}


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
          console.log(resData);
        }, resError => {
          console.log(resError);
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
