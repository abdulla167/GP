import { Component, OnInit } from '@angular/core';
import {Subscription} from "rxjs";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isCollapsed = true;
  companyTitle:string = 'WELADTY';
  isAuthenticated = false;
  logoUrl:string = "../../assets/images/logo.svg";
  leftNavItems:any[] = [["Home", "/home",true],["Baby Vaccinations","#",true], ["Healthy food","#",true]]
  rightNavItems:any[] = [["Login","/login",false], ["Sign Up", "/signup", false],["Profile","/profile", true]]
  userSubscription : Subscription;



  constructor(private authService : AuthenticationService) { }

  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe(theUser =>{
      this.isAuthenticated = (theUser != null ? true : false);
    })
  }

}
