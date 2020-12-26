import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  logoUrl:string =  "../../assets/images/logo.svg";

  constructor() { }

  ngOnInit(): void {
  }

}
