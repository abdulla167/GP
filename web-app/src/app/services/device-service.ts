import {Injectable, NgZone} from '@angular/core';

import {Observable, Subject} from 'rxjs';
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
export class DeviceService {

   public monitoringDevices : DeviceModel[] = [];

   graphsSubject: Subject<string>;

   babiesIssues : Subject <boolean>;

  constructor(private http: HttpClient, private tokenService: TokenService, private zone : NgZone) {
    this.graphsSubject = new Subject<string>();
    this.babiesIssues = new Subject<boolean>()
  }

  addDevice(deviceId : number, babyName : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    const body = new DeviceModel();
    body.deviceId = deviceId;
    body.babyName = babyName;
    return this.http.post('http://localhost:8080/device', body , {observe: 'response', headers});
  }

  getDevices(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/devices',{observe: 'response', headers});
  }

  connectDevices(){
    let observables : Observable<any>[] = [];
    for (let device in this.monitoringDevices){
      let observable = new Observable<any>(observer => {
        let deviceId = this.monitoringDevices[device].deviceId;
        const eventSource = new EventSource('http://localhost:8080/device/'+deviceId + '/subscription');
        eventSource.onmessage = event => {
          this.zone.run(() => {
            observer.next(event.data);
          })
        }
        eventSource.onerror = error => {
          this.zone.run(() => {
            observer.error(error);
          })
        }
      })
      observables.push(observable);
    }
    return observables;
  }

  getBabiesIssues(){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/babies/issues',{observe: 'response', headers});
  }

  getGraphSubject(){
    return this.graphsSubject;
  }

}
