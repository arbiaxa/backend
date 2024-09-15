package tn.esprit.esprobackend.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.esprobackend.entities.position;

import java.util.List;

public interface positionRepo extends JpaRepository<position,Long>{

}
