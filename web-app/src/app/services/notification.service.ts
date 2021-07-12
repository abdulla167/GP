import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable({
  providedIn : "root"
})
export class NotificationService{
  public notifications : string[] = [];

  constructor() {}

  subscribeForNotification(){
    return new Observable<boolean>(observer => {
      const eventSource = new EventSource('http://localhost:8080/connect/abdo')
      eventSource.onmessage = event => {
        this.notifications.push(event.data);
        observer.next(true);
      }
      eventSource.onerror = error => {
        observer.next(false);
      }
    })
  }

}
