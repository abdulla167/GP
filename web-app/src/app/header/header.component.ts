import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';

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
  leftNavItems: any[] = [['Baby Vaccinations', '#', true], ['Healthy food', '#', true], ['Training', '#', true]];
  rightNavItems: any[] = [['Login', '/login', false], ['Sign Up', '/signup', false], ['Profile', '/profile', true]
    , ['TimeLine', '/timeline', false]];
  userSubscription: Subscription;

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe(theUser => {
      this.isAuthenticated = (theUser != null ? true : false);
    });
  }

}
