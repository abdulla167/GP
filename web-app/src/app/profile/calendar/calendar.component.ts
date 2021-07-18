import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  OnInit, ChangeDetectorRef, NgZone, Input, DoCheck
} from '@angular/core';
import {
  startOfDay,
  endOfDay,
  subDays,
  addDays,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
} from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import {EventsService} from "../../services/events.service";
import {MatDialog} from '@angular/material/dialog';
import {EventChoiceComponent} from "./event-choice/event-choice.component";


@Component({
  selector: 'app-calendar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  view: CalendarView = CalendarView.Month;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  refresh : Subject<any>;

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-fw fa-pencil-alt"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        console.log(event.id)
        console.log(event.title)
        console.log(event.color.primary)
        this.eventsService.populateEvent(event);
        const dialogConfig = {
          autoFocus : true,
          data : {
            num : 'edit'
          }
        };
        this.dialog.open(EventChoiceComponent, dialogConfig);
      },
    },
    {
      label: '<i class="fas fa-fw fa-trash-alt"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.eventsService.events.filter((iEvent) => iEvent !== event);
        this.changeDetectorRef.detectChanges();
      },
    },
  ];


  events: CalendarEvent[];

  activeDayIsOpen: boolean = true;

  constructor(private modal: NgbModal, private eventsService : EventsService, private dialog: MatDialog, private changeDetectorRef: ChangeDetectorRef, private ngZone : NgZone) {
    this.events = this.eventsService.events;
    for ( let i = 0; i < this.events.length ; i++){
      this.events[i].actions = this.actions;
    }
  }

  ngOnInit(): void {
    this.events = [...this.eventsService.events];
    this.eventsService.addedEvents.subscribe(value => {
      console.log("notified")
      this.events = [...this.eventsService.events];
      this.changeDetectorRef.detectChanges();
    });
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({
                      event,
                      newStart,
                      newEnd,
                    }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.editEvent('Dropped or resized', event);
  }

  editEvent(action: string, event: CalendarEvent): void {
    console.log(event)
    this.eventsService.populateEvent(event);
    const dialogConfig = {
      autoFocus : true,
      data : {
        num : action
      }
    };
    this.dialog.open(EventChoiceComponent, dialogConfig);
  }

  addEvent(): void {
    const dialogConfig = {
      autoFocus : true,
      data : {
        num : 'add'
      }
    };
    this.dialog.open(EventChoiceComponent, dialogConfig);
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

}
