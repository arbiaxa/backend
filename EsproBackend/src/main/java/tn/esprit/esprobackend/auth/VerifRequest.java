package tn.esprit.esprobackend.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@Builder
@RequiredArgsConstructor
public class VerifRequest {
    private String email;
    private String code;
}
