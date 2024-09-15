package tn.esprit.esprobackend.entities;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.security.auth.Subject;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class user implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idU;
    private String nameU;
    private String surnameU;
    private String password;

    @Column(unique = true)
    private String email;
    private Long  telnum;
    private boolean accountLocked;
    private boolean enabled;

   private boolean mfaEnabled;
    private String secretKey;
//arbeyaaaaaaaaaaaaaa

    //
    //automatiquement tracké
@CreatedDate
@Column(nullable=false,updatable=false)
    private LocalDateTime createdD;

    @LastModifiedDate
    @Column(insertable=false)
    private LocalDateTime lastModifiedD;


    @Enumerated(EnumType.STRING)
    private Role role;

    private String img; //Bolb

    // cascade=CascadeType.ALL: non logique si j ajoute un user j ajoute une position : same operation
    @ManyToMany(cascade= CascadeType.PERSIST,fetch=FetchType.EAGER)//si recupere un user ses posions seront recuperes aussi
    List <position> positions;


    @ManyToMany(cascade=CascadeType.ALL)
    List <academicSp> acadmics;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
