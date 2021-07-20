import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserService} from "./user.service";

@Injectable({
  providedIn : "root"
})
export class NotificationService{
  public notifications : string[] = [];

  constructor(private userService : UserService) {}

  subscribeForNotification(){
    return new Observable<boolean>(observer => {
      const eventSource = new EventSource('http://localhost:8080/connectUser/' + this.userService.theUser.username)
      eventSource.onmessage = event => {
        this.notifications.push(event.data);
        observer.next(true);
      }
      eventSource.onerror = error => {
        observer.next(false);
        eventSource.close();
      }
    })
  }

}
