package com.server.mothercare.rest.user;

import com.server.mothercare.entities.ConfirmationToken;
import com.server.mothercare.entities.User;
import com.server.mothercare.repositories.ConfirmationTokenRepository;
import com.server.mothercare.security.config.EmailSenderService;
import com.server.mothercare.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationController {
   private BCryptPasswordEncoder encoder;
   private UserService userService;
   private EmailSenderService emailSenderService;
   private ConfirmationTokenRepository confirmationTokenRepository;
   @Autowired
   public RegistrationController(BCryptPasswordEncoder encoder,
                                 UserService userService,
                                 EmailSenderService emailSenderService,
                                 ConfirmationTokenRepository confirmationTokenRepository
   ){
      this.encoder = encoder;
      this.userService = userService;
      this.emailSenderService = emailSenderService;
      this.confirmationTokenRepository= confirmationTokenRepository;
   }

   @PostMapping(value = "/register/newUser")
   public ResponseEntity registerUser(@RequestBody User user){
      User dbUser = null;
      dbUser = this.userService.userbyUserName(user.getUsername());
      if (dbUser == null) {
         user.setPassword(this.encoder.encode(user.getPassword()));
         this.userService.registerUser(user);
         confirm(user.getEmail(),user);
         return new ResponseEntity("\"Sucessful sign up\"", HttpStatus.OK);
      }else {
         return new ResponseEntity("\"User alredy exist\"", HttpStatus.CONFLICT);
      }
   }
   private void confirm(String email,User theUser){
      ConfirmationToken confirmationToken = new ConfirmationToken(theUser);

      confirmationTokenRepository.save(confirmationToken);

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(email);
      mailMessage.setSubject("Complete Registration!");
      mailMessage.setFrom("kemo.antemo7@gmail.com");
      mailMessage.setText("To confirm your account, please click here : "
              +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

      emailSenderService.sendEmail(mailMessage);
   }

   @GetMapping("/confirm-account")
   public void confirmUserAccount( @RequestParam("token")String confirmationToken)
   {
      ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
      if(token != null)
      {
         User user = userService.userbyUserName(token.getUser().getUsername());
         user.setConfirmed(true);
         userService.update(user);
         System.out.println("Success token");
      }
      else
      {
         System.out.println("Failure token");
      }
   }

}