import {Injectable} from '@angular/core';

import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./Token.service";
import {DeviceModel} from "../models/device.model";

interface SensorReads{
  temp: number;
  heartRate: number;
  respiratoryRate: number;
  time: any;
}

@Injectable({
  providedIn : 'root'
})
export class SensorsService{

  public monitoringDevices : DeviceModel[];

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  addDevice(deviceId : number, babyName : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    const body = new DeviceModel();
    body.deviceId = deviceId;
    body.babyName = babyName;
    return this.http.post('http://localhost:8080/addDevice', body , {observe: 'response', headers});
  }

  getDevices(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/getDevices',{observe: 'response', headers}).subscribe(resp => {
      let devices = resp.body;
      for (let deviceNum in devices){
        let newDevice = new DeviceModel();
        newDevice.deviceId = devices[deviceNum].deviceId;
        newDevice.babyName = devices[deviceNum].babyName;
        this.monitoringDevices.push(newDevice);
      }
    });
  }

  connectDevices(){

  }

  // getUserDevices(){
  //   const headers = {
  //     Authorization: 'Bearer ' + this.tokenService.getToken(),
  //     'Content-type': 'application/json'
  //   };
  //   return this.http.get('http://localhost:8080/device/subscribe',{observe: 'response', headers});
  // }
  //
  // subscribeDevice(deviceId : string){
  //   const headers = {
  //     Authorization: 'Bearer ' + this.tokenService.getToken(),
  //     'Content-type': 'application/json'
  //   };
  //   const body = new DeviceModel();
  //   body.deviceId = deviceId;
  //   return this.http.post('http://localhost:8080/device/subscribe', body,{observe: 'response', headers});
  // }

}
