package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.PatientDto;
import com.niewhic.vetclinic.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm-email")
public class ConfirmEmailController {
    private final TokenService tokenService;

    @Operation(summary = "Confirms patient's appointment", description = "Confirms appointment and delete used token")
    @Parameters(value = {
            @Parameter(name = "token", description = "Token generated for appointment confirmation")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment confirmed", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid token supplied", content = @Content)})
    @GetMapping
    public ResponseEntity<Void> confirmEmail(@RequestParam("token") String token) {
        if (tokenService.confirmEmail(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}