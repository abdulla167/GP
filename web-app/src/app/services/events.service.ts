import {CalendarEvent, CalendarEventAction} from "angular-calendar";
import {addDays, addHours, endOfMonth, startOfDay, subDays} from "date-fns";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {EventChoiceComponent} from "../profile/calendar/event-choice/event-choice.component";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {EventModel} from "../models/event.model";
import {TokenService} from "./Token.service";
import {any} from "codelyzer/util/function";




@Injectable({
  providedIn : 'root'
})
export class EventsService{
  form : FormGroup = new FormGroup({
    title : new FormControl(null),
    primaryColor :new FormControl(''),
    secondaryColor :new FormControl(''),
    start :new FormControl(''),
    end : new FormControl(null),
  })

  events: CalendarEvent[] = [];

  color = {primary: "", secondary: ""}

  constructor(private http : HttpClient, private tokenService : TokenService) {
  }

  event : CalendarEvent = {
    title: '',
    color : this.color,
    start : new Date(),
    end : new Date(),
    draggable : true,
    id : null
  }

  addedEvents = new Subject<boolean>();

  addEvent (event : CalendarEvent, reminder : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    let userEvent = new EventModel();
    userEvent.title = event.title;
    userEvent.endDate = event.end;
    userEvent.startDate = event.start;
    userEvent.primaryColor = event.color.primary;
    console.log("primary color : " + event.color.primary)
    userEvent.secondaryColor = event.color.secondary;
    if (reminder == "yes"){
      userEvent.reminder = true;
    } else {
      userEvent.reminder = false;
    }
    return this.http.post('http://localhost:8080/addEvent', userEvent, {observe: 'response', headers}).subscribe(resData => {
      let newEvent : any = resData.body;
      event.id = newEvent.id;
      this.events.push(event);
      this.addedEvents.next(true);
    });
  }

  editEvent(event : CalendarEvent){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    console.log(event);
    let userEvent = new EventModel();
    userEvent.id = +event.id;
    userEvent.title = event.title;
    userEvent.endDate = event.end;
    userEvent.startDate = event.start;
    userEvent.primaryColor = event.color.primary;
    userEvent.secondaryColor = event.color.secondary;
    console.log(userEvent)
    return this.http.post('http://localhost:8080/editEvent', userEvent, {observe: 'response', headers}).subscribe(resData => {
      let editedEvent : any = resData.body;
      let index = this.events.findIndex(event1 => event1.id == editedEvent.id);
      this.events[index] = event;
      this.addedEvents.next(true);
    });
  }



  deleteEvent(eventId: number){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    return this.http.post('http://localhost:8080/deleteEvent/{eventId}', {observe: 'response', headers}).subscribe(resData => {
      let index = this.events.findIndex(oldEvent => oldEvent.id == eventId);
      this.events.splice(index, 1);
    });
    this.addedEvents.next(true);
  }

  populateEvent(event){
    console.log("event populated : " + this.event)
    this.event.id = event.id;
    this.event.title = event.title;
    this.color.primary = event.color.primary;
    this.color.secondary = event.color.secondary;
    this.event.start = event.start;
    this.event.end = event.end;
  }

  addMonths(date, months) {
    let d = date.getDate();
    date.setMonth(date.getMonth() + +months);
    if (date.getDate() != d) {
      date.setDate(0);
    }
    return date;
  }

}
