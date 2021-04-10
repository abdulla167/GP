import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading:boolean = false;
  error : string;

  constructor(private authService : AuthenticationService) { }

  ngOnInit(): void {
  }

  onSubmitForm(form: NgForm){
    let username :string = form.value.username;
    let password : string = form.value.password;
    this.isLoading = true;
    this.authService.login(username, password);
    //   .subscribe(respData => {
    //   console.log(respData);
    // }, respError => {
    //   respError = this.error;
    //   console.log(respError);
    // });
    this.isLoading = false;
  }
}
