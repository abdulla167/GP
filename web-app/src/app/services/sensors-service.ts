import {Injectable} from '@angular/core';
import {io} from 'socket.io-client';
import {Observable} from 'rxjs';

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

  socket: any;

  constructor() {
    this.socket = io('http//localhost:4200');

  }

  getReading(): Observable<SensorReads>{
    return new Observable<SensorReads>((subscriber => {
      this.socket.on((data) => {
        subscriber.next(data);
      });
    }));
  }

}
