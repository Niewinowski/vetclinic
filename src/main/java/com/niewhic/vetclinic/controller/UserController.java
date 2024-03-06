package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.office.OfficeDto;
import com.niewhic.vetclinic.model.user.UserDto;
import com.niewhic.vetclinic.model.user.command.CreateUserCommand;
import com.niewhic.vetclinic.model.user.User;
import com.niewhic.vetclinic.security.UserRole;
import com.niewhic.vetclinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Add a new user", description = "Creates a new user entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user details supplied", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Validated @RequestBody CreateUserCommand command) {
        User user = modelMapper.map(command, User.class);
        user.setRole(UserRole.valueOf(command.getRole()));
        User savedUser = userService.save(user);
        UserDto userDto = modelMapper.map(savedUser, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
