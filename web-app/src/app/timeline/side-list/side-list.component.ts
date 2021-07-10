import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {CreateBlogComponent} from '../create-blog/create-blog.component';

@Component({
  selector: 'app-side-list',
  templateUrl: './side-list.component.html',
  styleUrls: ['./side-list.component.css']
})
export class SideListComponent implements OnInit {

  @ViewChild('drawer') drawer;
  opened = true;
  showButton = false;
  alwaysOpened = true;
  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    const toggle = window.innerWidth  > 800 ? true : false;
    this.responsiveSideNav(toggle);
  }

  onResize(event) {
    const toggle = event.target.innerWidth > 800 ? true : false;
    this.responsiveSideNav(toggle);
  }

  responsiveSideNav(toggle){
    if (toggle){
      this.showButton = false;
      if (this.alwaysOpened === false){
        this.alwaysOpened = true;
        if (this.opened === false){
          this.drawer.toggle();
          this.opened = true;
        }
      }
    }else{
      this.showButton = true;
      if ( this.alwaysOpened === true){
        this.alwaysOpened = false;
        if (this.opened === true){
          this.drawer.toggle();
          this.opened = false;
        }
      }
    }
  }

  openDialog(){
   const dialogRef = this.dialog.open(CreateBlogComponent);

   dialogRef.afterClosed().subscribe(result => {
     console.log(`Dialog result: ${result}`);
   });
  }
}
