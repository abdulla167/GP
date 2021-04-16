import {CalendarEvent, CalendarEventAction} from "angular-calendar";
import {addDays, addHours, endOfMonth, startOfDay, subDays} from "date-fns";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {EventChoiceComponent} from "../profile/calendar/event-choice/event-choice.component";




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

  event : CalendarEvent = {
    title: '',
    color : this.color,
    start : new Date(),
    end : new Date(),
    draggable : true
  }

  addedEvents = new Subject<boolean>();

  addEvent (event : CalendarEvent){
    this.events = [...this.events, event];
    this.addedEvents.next(true);
  }

  editEvent(event : CalendarEvent){
    let index = this.events.findIndex(oldEvent => oldEvent.id == event.id);
    console.log(index)
    this.events[index].title = event.title;
    this.events[index].start = event.start;
    this.events[index].end = event.end;
    this.events[index].color = event.color;
    this.addedEvents.next(true);
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
