package tn.esprit.esprobackend.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.esprobackend.entities.user;




@Repository
public interface userRepo extends JpaRepository<user,Long>{
 Optional<user> findByEmail(String email);
    List<user> findByEnabled(boolean enabled);
    @Transactional
    @Modifying
     @Query("SELECT p.nameP, COUNT(u.idU) FROM user u inner JOIN u.positions p GROUP BY p.idP")
   // @Query("SELECT p.nameP, COUNT(u.idU) FROM position p LEFT JOIN user u ON u.idU IN (SELECT u.idU FROM u.positions p) GROUP BY p.idP")
    //List<Object[]> groupPositionsByUserCount();


   /* @Query(value = "SELECT p.nameP, COUNT(up.user_idu) " +
            "FROM position p " +
            "LEFT JOIN user_positions up ON p.idP = up.positions_idp " +
            "GROUP BY p.idP", nativeQuery = true)*/
    List<Object[]> groupPositionsByUserCount();


    @Transactional
    @Modifying
    @Query("update user u set u.password= ?2 where u.email= ?1")
    void updatePassword(String email,String password);

    @Transactional
    @Modifying
    @Query("update user u set u.secretKey= ?2 where u.email= ?1")
    void updateSecretKey(String email,String secretKey);



    @Transactional
    @Modifying
   @Query("update user u set u.mfaEnabled= ?2 where u.email= ?1")
   void updateMfaEnabled(String email,boolean mfaEnabled);

    @Transactional
    @Modifying
    @Query(value = "SELECT p.idP " +
            "FROM position p " +
            "LEFT JOIN user_positions up ON p.idP = up.positions_idp " +
            "WHERE up.user_idu IS NULL", nativeQuery = true)
    List<Long> findUnassignedPositions();
}
