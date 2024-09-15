package tn.esprit.esprobackend.auth;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailBody {

    //String to;
    String text;
    String subject;
}
