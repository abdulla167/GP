import {Injectable, NgZone} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {TokenService} from './Token.service';
import {DeviceModel} from '../models/device.model';


@Injectable({
  providedIn : 'root'
})
export class DeviceService {

   public monitoringDevices: DeviceModel[] = [];

   graphsSubject: Subject<string>;

   babiesIssues: Subject <boolean>;

  constructor(private http: HttpClient, private tokenService: TokenService, private zone: NgZone) {
    this.graphsSubject = new Subject<string>();
    this.babiesIssues = new Subject<boolean>();
  }

  addDevice(deviceId: number, babyName: string): Observable<HttpResponse<object>>{
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    const body = new DeviceModel();
    body.deviceId = deviceId;
    body.babyName = babyName;
    return this.http.post('http://localhost:8080/device', body , {observe: 'response', headers});
  }

  getDevices(): Observable<HttpResponse<object>>{
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/devices', {observe: 'response', headers});
  }

  connectDevices(): Observable<any>[]{
    const observables: Observable<any>[] = [];
    for (const device in this.monitoringDevices){
      const observable = new Observable<any>(observer => {
        const deviceId = this.monitoringDevices[device].deviceId;
        const eventSource = new EventSource('http://localhost:8080/device/' + deviceId + '/subscription');
        eventSource.onmessage = event => {
          this.zone.run(() => {
            observer.next(event.data);
          });
        };
        eventSource.onerror = error => {
          this.zone.run(() => {
            observer.error(error);
          });
        };
      });
      observables.push(observable);
    }
    return observables;
  }

  getBabiesIssues(): Observable<HttpResponse<object>>{
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getAccessToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/babies/issues', {observe: 'response', headers});
  }




  getGraphSubject(): Subject<string>{
    return this.graphsSubject;
  }

}
