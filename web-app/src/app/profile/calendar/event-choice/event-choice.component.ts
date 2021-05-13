import { Component, OnInit } from '@angular/core';
import {Subject} from "rxjs";
import {EventsService} from "../../../services/events.service";
import {addDays, endOfMonth, subDays} from "date-fns";
import {CalendarEvent} from "angular-calendar";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-event-choice',
  templateUrl: './event-choice.component.html',
  styleUrls: ['./event-choice.component.css']
})
export class EventChoiceComponent implements OnInit {
  date = new Date();
  event : CalendarEvent;

  constructor(public eventsService : EventsService,  public dialogRef : MatDialogRef<EventChoiceComponent>) {}

  ngOnInit(): void {
    this.event = {
      color: this.eventsService.event.color,
      end: this.eventsService.event.end,
      start: this.eventsService.event.start,
      title: this.eventsService.event.title,
      id : this.eventsService.event.id
    }
  }


  addEvent(){
    console.log(this.event)
    this.eventsService.addEvent(this.event);
    this.onClose();
  }

  editEvent(){
    this.eventsService.editEvent(this.event);
    this.onClose();
  }

  deleteEvent(){
    this.eventsService.deleteEvent(+this.event.id);
    this.onClose();
  }

  onClose(){
    this.dialogRef.close();
    // this.resetEvent();
  }

  resetEvent(){
    this.event.title ='';
    this.event.start = new Date();
    this.event.end = new Date();
    this.event.color.primary ='';
    this.event.color.secondary='';
  }
}
