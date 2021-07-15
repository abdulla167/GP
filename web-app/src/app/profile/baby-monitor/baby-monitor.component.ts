import {ChangeDetectorRef, Component, NgZone, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {TempChartComponent} from './temp-chart/temp-chart.component';
import {HeartRateChartComponent} from './heart-rate-chart/heart-rate-chart.component';
import {RespirChartComponent} from './respir-chart/respir-chart.component';
import {DeviceService} from "../../services/device-service";
import {Observable, Subscription} from "rxjs";
import {NotificationService} from "../../services/notification.service";
import {DeviceModel} from "../../models/device.model";
import {copyArrayItem} from "@angular/cdk/drag-drop";
import {AdditionalInfoComponent} from "../../auth/additional-info/additional-info.component";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'baby-monitor',
  templateUrl: './baby-monitor.component.html',
  styleUrls: ['./baby-monitor.component.css']
  })
export class BabyMonitorComponent implements OnInit {
  subscriptions : Subscription[] = [];
  devices : DeviceModel[] = []
  heartRateRead = 0;
  tempRead = 0 ;
  spo2Read = 0;


  constructor(private dialog: MatDialog, private  sensorService: DeviceService, private notificationService: NotificationService, public zone: NgZone, private userService : UserService) {}

  ngOnInit() {
    this.updateDevice();
    this.notificationService.subscribeForNotification().subscribe(data => {
      if (data == true){
        while (this.notificationService.notifications.length > 0){
          let message = this.notificationService.notifications.pop();
          console.log(message)
          switch (message){
            case "connected":
              this.updateDevice();
              console.log("update system devices")
              break;
            case "disconnected":
              this.updateDevice();
              break;
            case "event_alarm":
              break;
          }
        }
      }else {
        console.log("error in notification")
      }
    })
  }

  updateDevice(){
    this.sensorService.getDevices().subscribe(resp => {
      let devices = resp.body;
      this.sensorService.monitoringDevices = []
      this.devices = []
      for (let deviceNum in devices){
        let newDevice = new DeviceModel();
        newDevice.deviceId = devices[deviceNum].deviceId;
        newDevice.babyName = devices[deviceNum].babyName;
        this.sensorService.monitoringDevices.push(newDevice);
        this.devices.push(newDevice);
      }
      console.log(this.devices)
    });
    setTimeout(() =>{
      this.zone.run(() =>{
        let observables = this.sensorService.connectDevices();
        for (let observable in observables){
          let subscription = observables[observable].subscribe(data =>{
            let dataJson = JSON.parse(data)
            this.sensorService.monitoringDevices[observable].tempReads.tempRead.push(dataJson.tempRead.value);
            this.sensorService.monitoringDevices[observable].tempReads.readTime.push(dataJson.tempRead.time);
            this.tempRead = dataJson.tempRead.value;
            this.sensorService.monitoringDevices[observable].heartRateReads.heartRateRead.push(dataJson.heartRateRead.value);
            this.sensorService.monitoringDevices[observable].heartRateReads.readTime.push(dataJson.heartRateRead.time);
            this.heartRateRead = dataJson.heartRateRead.value;
            this.sensorService.monitoringDevices[observable].spo2Reads.spo2Read.push(dataJson.spo2Read.value);
            this.sensorService.monitoringDevices[observable].spo2Reads.readTime.push(dataJson.spo2Read.time);
            this.spo2Read = dataJson.spo2Read.value;
            this.sensorService.graphsSubject.next(observable);
          })
          this.subscriptions.push(subscription);
        }
      })
    }, 50);
    for (let device of this.sensorService.monitoringDevices){
      this.devices.push(device)
    }
  }

  addNewDevice(deviceId, babyName){
    console.log("ok")
    this.sensorService.addDevice(deviceId, babyName).subscribe((response) => {
      if (response.status === 200) {
        console.log('Device added successfully');
        this.updateDevice();
      } else {
        console.log('Device can not be added successfully');
      }
    });
  }

  showGraph(graphName: string, deviceId : number): void{
    const dialogConfig = {
      autoFocus : true,
      data : {
        num : deviceId
      }
    };
    let dialogRef;
    switch (graphName){
      case 'temp':
        dialogRef = this.dialog.open(TempChartComponent, dialogConfig);
        break;
      case 'heart':
        dialogRef = this.dialog.open(HeartRateChartComponent, dialogConfig);
        break;
      case 'respir':
        dialogRef = this.dialog.open(RespirChartComponent, dialogConfig);
        break;
    }
  }
}
