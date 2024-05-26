package com.pdev.emailapi.controller;

import com.pdev.emailapi.model.EmailRequest;
import com.pdev.emailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String welcome() {
        return "Hello";
    }

  // API to send email
  @PostMapping("/sendemail")
  public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) { // in this @RequestBody is used to get the data from body after making request
      boolean isSent = emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());
      if (isSent) {
          return ResponseEntity.ok("{\"message\": \"Email sent successfully!\"}");
      } else {
          return ResponseEntity.status(500).body("{\"message\": \"Email sending failed.\"}");
        }
    }

//    @PostMapping("/sendemail")
//    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequest emailRequest) {
//        // Your email sending logic here
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Email sent successfully!");
//        return ResponseEntity.ok(response);
//    }



}
