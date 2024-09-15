package tn.esprit.esprobackend.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.esprobackend.entities.Role;
import tn.esprit.esprobackend.entities.user;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
//reponse va etre le token genre
  //@JsonProperty("access_token")
  private String jwt;
  /*@JsonProperty("refresh_token")
  private String refreshToken;*/
  private String   nameU;

  private String  surnameU;
  private Role role;
  private Long  idU;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean mfaEnabled;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String secretImageUri;

}
