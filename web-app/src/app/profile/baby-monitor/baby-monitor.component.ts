import {Component} from "@angular/core";

@Component({
  selector: 'baby-monitor',
  templateUrl: './baby-monitor.component.html',
  styleUrls: ['./baby-monitor.component.css']
  })
export class BabyMonitorComponent{
  heartSensor:number = 14;
  tempSensor:number =37 ;

}
