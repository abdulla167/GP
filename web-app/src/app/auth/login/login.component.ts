import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication.service";
import {TokenService} from "../../services/Token.service";
import {Router} from "@angular/router";
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading:boolean = false;
  error : string;

  constructor(private authService : AuthenticationService, private tokenService: TokenService, private router: Router, private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmitForm(form: NgForm){
    let username :string = form.value.username;
    let password : string = form.value.password;
    this.isLoading = true;
    this.authService.login(username, password)
      .subscribe(( response) => {
        if (response.status === 200 ){
          this.tokenService.saveToken( response.body['access_token']);
          this.userService.getUser();
          this.router.navigate(['/profile']);
        }
      }, resError => {
        this.error = resError;
      });
    this.isLoading = false;
  }
}
