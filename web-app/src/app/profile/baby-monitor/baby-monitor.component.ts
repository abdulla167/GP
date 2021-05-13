import {Component} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {TempChartComponent} from './temp-chart/temp-chart.component';
import {HeartRateChartComponent} from './heart-rate-chart/heart-rate-chart.component';
import {RespirChartComponent} from './respir-chart/respir-chart.component';

@Component({
  selector: 'baby-monitor',
  templateUrl: './baby-monitor.component.html',
  styleUrls: ['./baby-monitor.component.css']
  })
export class BabyMonitorComponent{
  heartSensor = 14;
  tempSensor = 37 ;

  constructor(private dialog: MatDialog) {}

  showGraph(graphName: string): void{
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '100%';

    switch (graphName){
      case 'temp':
        console.log("ok");
        this.dialog.open(TempChartComponent);
        break;
      case 'heart':
        this.dialog.open(HeartRateChartComponent);
        break;
      case 'respir':
        this.dialog.open(RespirChartComponent);
        break;
    }
  }
}
