import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SensorsService} from "../../../services/sensors-service";
import {Chart} from '../../../../../node_modules/chart.js';
import {Subscription} from "rxjs";


@Component({
  selector: 'app-respir-chart',
  templateUrl: './respir-chart.component.html',
  styleUrls: ['./respir-chart.component.css']
})
export class RespirChartComponent implements OnInit {
  myChart : Chart;
  currentChart;
  graphUpdateSubscription : Subscription;
  options = {
    scales: {
      xAxes: [{
        barPercentage: 0.8,
        barThickness: 8,
        maxBarThickness: 10,
        minBarLength: 2,
        gridLines: {
          offsetGridLines: true
        },
        yAxes: [{
          ticks: {
            min: 0,
            max: 50,
            stepSize: 10
          }
        }]
      }],
      layout: {
        padding: {
          left: 50,
          right: 0,
          top: 20,
          bottom: 0
        }
      }
    }
  }
  config = {
    type: 'line',
    data: {
      title: "Baby Heart Rate",
      labels: [],
      datasets: [{
        label: "Heart rate",
        borderColor: "rgba(54, 162, 235, 1)",
        fill : false,
        pointBackgroundColor : "rgba(54, 162, 235, 1)",
        pointHoverBorderColor : "rgba(100, 50, 85, 1)",
        pointHoverBackgroundColor : "rgba(54, 162, 235, 1)",
        borderWidth : 1,
        pointRadius : 4,
        pointHoverRadius : 8,
        data: []
      }],
      responsive: true,
      maintainAspectRatio : true,
      options: this.options
    }
  };

  constructor(private sensorService : SensorsService, public dialogRef: MatDialogRef<RespirChartComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.graphUpdateSubscription = this.sensorService.getGraphSubject().subscribe((graphNum) => {
      this.config.data.labels = this.sensorService.monitoringDevices[graphNum].spo2Reads.readTime;
      this.config.data.datasets[0].data = this.sensorService.monitoringDevices[graphNum].spo2Reads.spo2Read;
      if (this.currentChart == graphNum){
        this.myChart.update();
      }
    });
    let deviceIndex = this.sensorService.monitoringDevices.findIndex(device => device.deviceId == this.data.num);
    this.currentChart = deviceIndex;
    this.config.data.labels = this.sensorService.monitoringDevices[deviceIndex].spo2Reads.readTime;
    this.config.data.datasets[0].data = this.sensorService.monitoringDevices[deviceIndex].spo2Reads.spo2Read;
    this.myChart = new Chart('myChart', this.config);
  }
}
