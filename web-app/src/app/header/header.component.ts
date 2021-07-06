import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';
import {TokenService} from "../services/Token.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isCollapsed = true;
  companyTitle = 'MOTHER CARE';
  isAuthenticated = false;
  logoUrl = '../../assets/images/logo.svg';
  userSubscription: Subscription;

  constructor(private authService: AuthenticationService, public tokenService: TokenService) { }

  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe(theUser => {
      this.isAuthenticated = (theUser != null ? true : false);
    });
  }

}
