package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.service.EmailService;
import com.niewhic.vetclinic.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm-email")
public class ConfirmEmailController {

    private final EmailService emailService;
    private final TokenService tokenService;


    @GetMapping
    public ResponseEntity<Void> confirmEmail(@RequestParam("token") String token) {
        if (tokenService.confirmEmail(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}