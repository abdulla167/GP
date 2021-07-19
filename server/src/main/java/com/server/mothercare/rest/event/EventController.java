package com.server.mothercare.rest.event;

import com.server.mothercare.entities.Event;
import com.server.mothercare.entities.User;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/event")
public class EventController {

    UserService userService;

    @Autowired
    public EventController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity getUserEvents(Principal user){
        log.error("get devices");
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        User theUser = optionalUser.get();
        List<Event> eventsList = theUser.getEvents();
        return new ResponseEntity(eventsList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addEvent(@RequestBody Event event, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            user1.numOfEvents++;
            event.setId(user1.numOfEvents);
            user1.getEvents().add(event);
            this.userService.update(user1);
        });
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity editEvent(@RequestBody Event event, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            for (var e :
                    user1.getEvents()) {
                if (e.getId() == event.getId()){
                    e.setTitle(event.getTitle());
                    e.setReminder(event.isReminder());
                    e.setPrimaryColor(event.getPrimaryColor());
                    e.setSecondaryColor(event.getSecondaryColor());
                    e.setTitle(event.getTitle());
                    e.setStartDate(event.getStartDate());
                    e.setEndDate(event.getEndDate());
                    e.setDescription(event.getDescription());
                    e.setImage(event.getImage());
                }
            }
            this.userService.update(user1);
        });
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @GetMapping("/delete/{eventId}")
    public ResponseEntity deleteEvent(@PathVariable String eventId, Principal user){
        Optional<User> optionalUser = this.userService.getUserbyUserName(user.getName());
        optionalUser.ifPresent(user1 ->{
            log.error(user1.getUsername());
            var events = user1.getEvents();
            events.removeIf(event -> event.getId() == Long.valueOf(eventId));
            this.userService.update(user1);
        });
        return new ResponseEntity(HttpStatus.OK);
    }
}
