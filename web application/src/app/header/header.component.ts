import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  companyTitle:string = 'WELADTY';
  logoUrl:string = "../../assets/images/logo.svg";
  leftNavItems:any[] = [["Home", "/home"], ["Book Doctor","/bookdoctor"],["Book Babysitter","#"], ["Diet Programs","#"]]
  rightNavItems:any[] = [["Login","/login"], ["Sign Up", "/signup"]]



  constructor() { }

  ngOnInit(): void {
  }

}
