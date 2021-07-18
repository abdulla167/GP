import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../../models/user.model';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from '@angular/router';
import {MatDialog} from "@angular/material/dialog";
import {AdditionalInfoComponent} from "../additional-info/additional-info.component";
import {TempChartComponent} from "../../profile/baby-monitor/temp-chart/temp-chart.component";

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

  constructor(private authService: AuthenticationService, private router: Router) {}



  ngOnInit(): void {
    this.datepickerValue = new Date();
  }

  onSubmitForm(form: NgForm){
    if (!form.valid){
      return;
    }else {
      if (form.value.password === form.value.confirmPassword){
        this.invalidConfirmPassword = false;
        let user = new User(form.value.firstName, form.value.lastName, form.value.username,  form.value.password, this.defaultGender,
                            form.value.email,form.value.birthOfDate, form.value.phone);
        this.isLoading = true;
        this.authService.signup(user).subscribe(resData => {
          this.router.navigate(['/login']);
          form.reset();
        }, resError => {
          this.error = resError;
        });
        this.isLoading = false;
      }else {
        this.invalidConfirmPassword = true;
      }
    }
  }

}
