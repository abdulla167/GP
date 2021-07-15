package com.server.mothercare.rest.user;

import com.server.mothercare.entities.ConfirmationToken;
import com.server.mothercare.entities.User;
import com.server.mothercare.DAOs.ConfirmationTokenDAO;
import com.server.mothercare.models.Error;
import com.server.mothercare.services.EmailSenderService;
import com.server.mothercare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class RegistrationController {
    private BCryptPasswordEncoder encoder;
    private UserService userService;
    private EmailSenderService emailSenderService;
    private ConfirmationTokenDAO confirmationTokenDAO;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder encoder,
                                  UserService userService,
                                  EmailSenderService emailSenderService,
                                  ConfirmationTokenDAO confirmationTokenDAO
    ) {
        this.encoder = encoder;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenDAO = confirmationTokenDAO;
    }

    @PostMapping(value = "/register/newUser")
    public ResponseEntity registerUser(@RequestBody User user) {
        User dbUser = null;
        ResponseEntity responseEntity = null;
        try {
            this.userService.getUserbyUserName(user.getUsername());
            Error error = new Error("user_exist", "User is already exist");
            responseEntity = new ResponseEntity(error, HttpStatus.CONFLICT);
        } catch (UsernameNotFoundException usernameNotFoundException){
            user.setPassword(this.encoder.encode(user.getPassword()));
            try {
                var confirmationToken = confirm(user.getEmail(), user);
                this.userService.registerUser(user);
                this.confirmationTokenDAO.save(confirmationToken);
                responseEntity = new ResponseEntity(user, HttpStatus.OK);
            } catch (Exception e){
                log.warn(e.getMessage());
                Error error = new Error("server_problem", "Server has a problem now try again later");
                responseEntity = new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return responseEntity;
    }

    private ConfirmationToken confirm(String email, User theUser) {
        ConfirmationToken confirmationToken = new ConfirmationToken(theUser);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("abdullaelsayed167@yahoo.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
        return confirmationToken;
    }

    @GetMapping("/confirm-account")
    public void confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenDAO.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userService.userbyUserName(token.getUser().getUsername());
            user.setConfirmed(true);
            userService.update(user);
            System.out.println("Success token");
        } else {
            System.out.println("Failure token");
        }
    }
}