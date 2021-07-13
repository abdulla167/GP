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

  constructor(private dialog: MatDialog, private authService: AuthenticationService, private router: Router) {}



  ngOnInit(): void {
    this.datepickerValue = new Date();
  }

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
          const dialogConfig = {
            autoFocus : true,
            disableClose : true

          };
          this.router.navigate(['/login']);
          this.dialog.open(AdditionalInfoComponent, dialogConfig);
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
