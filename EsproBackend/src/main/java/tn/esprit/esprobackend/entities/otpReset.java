package tn.esprit.esprobackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class otpReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idO;
    private Date expirationTime;

    private Integer otp;

    @ManyToOne(cascade= CascadeType.ALL)//si recupere un user ses posions seront recuperes aussi
    private user userr;


}
