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

  constructor(private authService : AuthenticationService) { }

  ngOnInit(): void {
  }

  onSubmitForm(form: NgForm){
    let username :string = form.value.username;
    let password : string = form.value.password;
    this.authService.signin(username, password).subscribe(respData => {

    }, respError => {
      console.log("error");
    });


  }

}
