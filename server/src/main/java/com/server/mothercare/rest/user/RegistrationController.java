package com.server.mothercare.rest.user;

import com.server.mothercare.entities.ConfirmationToken;
import com.server.mothercare.entities.User;
import com.server.mothercare.DAOs.ConfirmationTokenDAO;
import com.server.mothercare.services.EmailSenderService;
import com.server.mothercare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
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
        dbUser = this.userService.userbyUserName(user.getUsername());
        if (dbUser == null) {
            user.setPassword(this.encoder.encode(user.getPassword()));
            this.userService.registerUser(user);
            confirm(user.getEmail(), user);
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity("\"User alredy exist\"", HttpStatus.CONFLICT);
        }
    }

    private void confirm(String email, User theUser) {
        ConfirmationToken confirmationToken = new ConfirmationToken(theUser);
        confirmationTokenDAO.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("abdullaelsayed167@yahoo.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
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