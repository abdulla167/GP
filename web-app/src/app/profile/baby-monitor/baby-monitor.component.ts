import {ChangeDetectorRef, Component, NgZone, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {TempChartComponent} from './temp-chart/temp-chart.component';
import {HeartRateChartComponent} from './heart-rate-chart/heart-rate-chart.component';
import {RespirChartComponent} from './respir-chart/respir-chart.component';
import {DeviceService} from '../../services/device-service';
import {Observable, Subscription} from 'rxjs';
import {NotificationService} from '../../services/notification.service';
import {DeviceModel} from '../../models/device.model';
import {copyArrayItem} from '@angular/cdk/drag-drop';
import {AdditionalInfoComponent} from '../../auth/additional-info/additional-info.component';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'baby-monitor',
  templateUrl: './baby-monitor.component.html',
  styleUrls: ['./baby-monitor.component.css']
  })
export class BabyMonitorComponent implements OnInit {
  subscriptions: Subscription[] = [];
  devices: DeviceModel[] = [];
  heartRateRead = 0;
  tempRead = 0 ;
  spo2Read = 0;
  positions: string[] = ['../../../assets/images/front.jpg', 'down-position', 'left-position', 'right-position'];
  currentPositions: string[] = [];


  constructor(private dialog: MatDialog, private  sensorService: DeviceService, private notificationService: NotificationService,
              public zone: NgZone, private userService: UserService) {}

  ngOnInit(): void{
    this.updateDevice();
    this.notificationService.subscribeForNotification().subscribe(data => {
      if (data === true){
        while (this.notificationService.notifications.length > 0){
          const message = this.notificationService.notifications.pop();
          switch (message){
            case 'connected':
              this.updateDevice();
              break;
            case 'disconnected':
              this.updateDevice();
              break;
            case 'baby_issue':
              this.sensorService.babiesIssues.next(true);
              break;
          }
        }
      }else {
        console.log('error in notification');
      }
    });
  }

  updateDevice(): void{
    this.sensorService.getDevices().subscribe(resp => {
      const devices = resp.body;
      this.sensorService.monitoringDevices = [];
      this.devices = [];
      for (const deviceNum in devices){
        const newDevice = new DeviceModel();
        newDevice.deviceId = devices[deviceNum].deviceId;
        newDevice.babyName = devices[deviceNum].babyName;
        this.sensorService.monitoringDevices.push(newDevice);
        this.devices.push(newDevice);
        this.currentPositions.push('');
      }
    });
    setTimeout(() => {
      this.zone.run(() => {
        const observables = this.sensorService.connectDevices();
        for (const observable in observables){
          const subscription = observables[observable].subscribe(data => {
            const dataJson = JSON.parse(data);
            this.sensorService.monitoringDevices[observable].tempReads.tempRead.push(dataJson.tempRead.value);
            this.sensorService.monitoringDevices[observable].tempReads.readTime.push(dataJson.tempRead.time);
            this.tempRead = dataJson.tempRead.value;
            this.sensorService.monitoringDevices[observable].heartRateReads.heartRateRead.push(dataJson.heartRateRead.value);
            this.sensorService.monitoringDevices[observable].heartRateReads.readTime.push(dataJson.heartRateRead.time);
            this.heartRateRead = dataJson.heartRateRead.value;
            this.sensorService.monitoringDevices[observable].spo2Reads.spo2Read.push(dataJson.spo2Read.value);
            this.sensorService.monitoringDevices[observable].spo2Reads.readTime.push(dataJson.spo2Read.time);
            const position = dataJson.spo2Read.value;
            this.spo2Read = dataJson.spo2Read.value;
            this.sensorService.graphsSubject.next(observable);
          });
          this.subscriptions.push(subscription);
        }
      });
    }, 50);
    for (const device of this.sensorService.monitoringDevices){
      this.devices.push(device);
    }
  }

  addNewDevice(deviceId, babyName): void{
    this.sensorService.addDevice(deviceId, babyName).subscribe((response) => {
      if (response.status === 200) {
        console.log('Device added successfully');
        this.updateDevice();
      } else {
        console.log('Device can not be added successfully');
      }
    });
  }

  showGraph(graphName: string, deviceId: number): void{
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
