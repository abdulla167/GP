import {Component, Inject, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {EventsService} from "../../../services/events.service";
import {addDays, endOfMonth, subDays} from "date-fns";
import {CalendarEvent} from "angular-calendar";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {EventModel} from "../../../models/event.model";

@Component({
  selector: 'app-event-choice',
  templateUrl: './event-choice.component.html',
  styleUrls: ['./event-choice.component.css']
})
export class EventChoiceComponent implements OnInit {
  date = new Date();
  event : CalendarEvent;
  actionType : string;
  reminder : string = "no";

  constructor(public eventsService : EventsService,  public dialogRef : MatDialogRef<EventChoiceComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.event= {
      id : this.eventsService.event.id,
      start : this.eventsService.event.start,
      end : this.eventsService.event.end,
      title : this.eventsService.event.title,
      color : this.eventsService.event.color
  }
    this.actionType = this.data.num;
  }


  addEvent(){
    console.log("add event : " + this.event);
    this.eventsService.addEvent(this.event, this.reminder);
    this.onClose();
  }

  editEvent(){
    console.log("edited event after:  " +this.event)
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
