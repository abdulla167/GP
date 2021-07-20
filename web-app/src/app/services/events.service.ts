import {CalendarEvent, CalendarEventAction} from "angular-calendar";
import {addDays, addHours, endOfMonth, startOfDay, subDays} from "date-fns";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {EventChoiceComponent} from "../profile/calendar/event-choice/event-choice.component";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
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

  events: EventModel[] = [];

  eventsCalendar : CalendarEvent[] = []

  upcomingEvents : EventModel[] = [];


  color = {primary: "", secondary: ""}

  constructor(private http : HttpClient, private tokenService : TokenService) {
  }

  event : EventModel = new EventModel();

  addedEvents = new Subject<boolean>();

  getEvents (){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/event/getAll', {observe: 'response', headers}).pipe(map((resp) => {
      let eventList : any = resp.body;
      this.events = [];
      this.eventsCalendar = [];
      for (let event of eventList) {
        let eventColor = {
          primary: event.primaryColor, secondary: event.secondaryColor
        }
        let savedEvent : CalendarEvent = {
          color: eventColor, draggable: true, end: new Date(event.endDate), id: event.id, start: new Date(event.startDate), title: event.title
        }
        this.events.push(event);
        this.eventsCalendar.push(savedEvent);
      }
      this.updateUpcomingEvents();
      this.addedEvents.next(true);
      return this.eventsCalendar;
    })
    );
  }

  addEvent (event : EventModel, reminder : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    if (reminder == "yes"){
      event.reminder = true;
    } else {
      event.reminder = false;
    }
    return this.http.post('http://localhost:8080/event/add', event, {observe: 'response', headers}).subscribe(resData => {
      let addedEvent : any = resData.body;
      let eventColor = {
        primary: addedEvent.primaryColor, secondary: addedEvent.secondaryColor
      }
      let savedEvent : CalendarEvent = {
        color: eventColor, draggable: true, end: new Date(addedEvent.endDate), id: addedEvent.id, start: new Date(addedEvent.startDate), title: addedEvent.title
      }
      event.id = addedEvent.id;
      this.events.push(event);
      this.eventsCalendar.push(savedEvent)
      this.updateUpcomingEvents();
      this.addedEvents.next(true);
    });
  }

  editEvent(event : EventModel, reminder : string){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    if (reminder == "yes"){
      event.reminder = true;
    } else {
      event.reminder = false;
    }
    return this.http.post('http://localhost:8080/event/edit', event, {observe: 'response', headers}).subscribe(resData => {
      let editedEvent : any = resData.body;
      let index = this.events.findIndex(event1 => event1.id == editedEvent.id);
      this.events[index] = event;
      let eventColor = {
        primary: editedEvent.primaryColor, secondary: editedEvent.secondaryColor
      }
      let savedEvent : CalendarEvent = {
        color: eventColor, draggable: true, end: new Date(editedEvent.endDate), id: editedEvent.id, start: new Date(editedEvent.startDate), title: editedEvent.title
      }
      this.eventsCalendar[index] = savedEvent;
      this.updateUpcomingEvents()
      this.addedEvents.next(true);
    });
  }



  deleteEvent(eventId: number){
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken(),
      'Content-type': 'application/json'
    };
    return this.http.get('http://localhost:8080/event/delete/' + eventId, {observe: 'response', headers}).subscribe(resData => {
      let index = this.events.findIndex(oldEvent => oldEvent.id == eventId);
      this.events.splice(index, 1);
      this.eventsCalendar.splice(index, 1)
      this.updateUpcomingEvents();
      this.addedEvents.next(true);
    });
  }

  populateEvent(event){
    this.event.id = event.id;
    this.event.title = event.title;
    this.color.primary = event.color.primary;
    this.color.secondary = event.color.secondary;
    this.event.startDate = event.start;
    this.event.endDate = event.end;
    let index = this.events.findIndex(event1 => event1.id == event.id)
    console.log(this.events)
    console.log(this.upcomingEvents)
    this.event.description = this.events[index].description;
    this.event.image = this.events[index].image;
  }

  updateUpcomingEvents(){
    let events = [...this.events];
    events.sort(function(o1,o2){
      if (o1.startDate < o2.startDate)    return -1;
      else if(o1.startDate > o2.startDate) return  1;
      else return  0;
    })
    this.upcomingEvents = events.slice(0,3);
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
