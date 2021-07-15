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

  colors: any = {
    red: {
      primary: '#ad2121',
      secondary: '#FAE3E3',
    },
    blue: {
      primary: '#1e90ff',
      secondary: '#D1E8FF',
    },
    yellow: {
      primary: '#e3bc08',
      secondary: '#FDF1BA',
    },
  };

  events: CalendarEvent[] = [
    {
      id : 1,
      start: startOfDay(new Date()),
      title: 'An event with no end date',
      color: this.colors.red,
    },
    {
      id : 2,
      start: subDays(endOfMonth(new Date()), 1),
      end: addDays(endOfMonth(new Date()), 1),
      title: 'A long event that spans 2 months',
      color: this.colors.blue,
      allDay: true,
    },
    {
      id : 3,
      start: addHours(startOfDay(new Date()), 2),
      end: addHours(new Date(), 2),
      title: 'A draggable and resizable event',
      color: this.colors.yellow,
      resizable: {
        beforeStart: true,
        afterEnd: true,
      },
      draggable: true,
    },
  ];

  color = {primary: "", secondary: ""}

  constructor(private http : HttpClient, private tokenService : TokenService) {
  }

  event : CalendarEvent = {
    title: '',
    color : this.color,
    start : new Date(),
    end : new Date(),
    draggable : true
  }

  addedEvents = new Subject<boolean>();

  // getEvents(event : CalendarEvent){
  //
  //
  //   return this.http.post('http://localhost:8080/oauth/token', body, {observe: 'response', headers});
  // }

  addOrEditEvent (event : CalendarEvent, reminder : string,  addOrEdit : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    let userEvent = new EventModel();
    userEvent.id = +this.event.id;
    userEvent.title = this.event.title;
    userEvent.endDate = this.event.end;
    userEvent.startDate = this.event.start;
    userEvent.primaryColor = this.event.color.primary;
    userEvent.secondaryColor = this.event.color.secondary;
    if (reminder == "yes"){
      userEvent.reminder = true;
    } else {
      userEvent.reminder = false;
    }
    switch (addOrEdit){
      case "add":
        return this.http.post('http://localhost:8080/addEvent', userEvent, {observe: 'response', headers}).subscribe(resData => {
          this.events.push(event);
          this.addedEvents.next(true);
        });
      case "edit":
        return this.http.post('http://localhost:8080/editEvent', userEvent, {observe: 'response', headers}).subscribe(resData => {
          let index = this.events.findIndex(event1 => event1.id == event.id);
          this.events[index] = event;
          this.addedEvents.next(true);
        });
    }
  }



  deleteEvent(eventId: number){
    let index = this.events.findIndex(oldEvent => oldEvent.id == eventId);
    this.events.splice(index, 1);
    this.addedEvents.next(true);
  }

  populateEvent(event){
    this.event.id = event.id;
    this.event.title = event.title;
    this.color.primary = event.color.primary;
    this.color.secondary = event.color.secondary;
    this.event.start = event.start;
    this.event.end = event.end;
  }

}
