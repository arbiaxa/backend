package tn.esprit.esprobackend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message="password is mandotary")
    @NotBlank(message="password is mandotary")
    @Size(min=5,message="Passwword should be 5 caracters minimum")
    private String password;


    @Email(message = "Email is not formatted")
    @NotEmpty(message="Email is mandotary")
    @NotBlank(message="Email is mandotary")
    private String email;


    private boolean mfaEnabled;
}
