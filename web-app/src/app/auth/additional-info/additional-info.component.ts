import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatStepperIntl} from "@angular/material/stepper";
import {MatDialogRef} from "@angular/material/dialog";
import {UserInfoModel} from "../../models/user-info.model";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-additional-info',
  templateUrl: './additional-info.component.html',
  styleUrls: ['./additional-info.component.css']
})
export class AdditionalInfoComponent implements OnInit {
  userInfo : UserInfoModel;
  error : string;

  constructor(public dialogRef: MatDialogRef<AdditionalInfoComponent>, private userService : UserService, private router: Router) {}

  ngOnInit(): void {}

  addUserInfo(){
    console.log(this.userInfo);
    this.userService.addUserInfo(this.userInfo).subscribe(resData => {
      this.dialogRef.close();
      this.router.navigate(['/profile']);
    }, resError => {
      this.error = resError;
    });
  }

  skipUserInfo(){
    this.dialogRef.close();
    this.router.navigate(['/profile']);
  }

}
