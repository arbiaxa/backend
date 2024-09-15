package tn.esprit.esprobackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.esprit.esprobackend.entities.otpReset;


import java.util.Optional;





import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.esprobackend.entities.user;

@Repository

public interface otpResetRepo extends JpaRepository<otpReset,Long> {
    Optional<otpReset> findByOtpAndUserr(Integer otp, user userr);
    // findByOtpAndUser

}
