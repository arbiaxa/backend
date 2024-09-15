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
public class academicSp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idAS;
    private String nameAS;

    @ManyToMany(mappedBy="acadmics",cascade=CascadeType.ALL)
    List<user> users;

}
