package tn.esprit.esprobackend.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idP;
    private String nameP;
    private Long shiftHours;



}
