package tn.esprit.esprobackend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import tn.esprit.esprobackend.entities.Role;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
@NotEmpty(message="Name is required")
////@NotBlank(message="Name is mandotary")
  private String nameU;

  @NotEmpty(message="surname is required")
  //@NotBlank(message="surnameU is mandotary")
  private String surnameU;


  @NotEmpty(message="password is required")
  //@NotBlank(message="password is mandotary")
  @Size(min=5,message="Passwword should be 5 caracters minimum")
  private String password;


  @Email(message = "Email is not formatted")
  @NotEmpty(message="Email is required")
  //@NotBlank(message="Email is mandotary")
  private String email;

  private Long  telnum;

  @NotEmpty(message="image is required")
  //@NotBlank(message="image is mandotary")
  private String img; //Bolb


 // private Role role;
}
