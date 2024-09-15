package tn.esprit.esprobackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.esprobackend.entities.academicSp;

@Repository
public interface acadamicRepo extends JpaRepository<academicSp,Long> {
}
