package develhope.Flow_Open_Spring.controller;

import develhope.Flow_Open_Spring.dto.NotificationDTO;
import develhope.Flow_Open_Spring.entities.User;
import develhope.Flow_Open_Spring.service.EmailService;
import develhope.Flow_Open_Spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity sendMail(@RequestBody NotificationDTO payload){
        try{
            User userToSendNotification = userService.getUserById(payload.getContactId());
            if (userToSendNotification == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find the answer");
            } else {
                emailService.sendTo(userToSendNotification.getEmail(), payload.getTitle(), payload.getText());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body("Mail sent! Please check your email");
    }
}