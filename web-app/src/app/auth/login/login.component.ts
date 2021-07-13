import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication.service";
import {TokenService} from "../../services/Token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading:boolean = false;
  error : string;

  constructor(private authService : AuthenticationService, private tokenService: TokenService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmitForm(form: NgForm){
    let username :string = form.value.username;
    let password : string = form.value.password;
    this.isLoading = true;
    this.authService.login(username, password)
      .subscribe(( response) => {
        console.log(response);
        if (response.status === 200 ){
          // this.tokenService.saveToken(JSON.parse(JSON.stringify(response.body)).get('access_token'));
          this.tokenService.saveToken( response.body['access_token']);
          console.log(this.tokenService.getToken());
          this.router.navigate(['/profile']);
        }
      }, resError => {
        console.log(resError)
        this.error = resError;
      });
    this.isLoading = false;
  }
}
