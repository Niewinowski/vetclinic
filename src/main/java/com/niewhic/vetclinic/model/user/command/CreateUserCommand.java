package com.niewhic.vetclinic.model.user.command;

import com.niewhic.vetclinic.security.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserCommand {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Pattern(regexp = "ADMIN|USER")
    private String role;
}
