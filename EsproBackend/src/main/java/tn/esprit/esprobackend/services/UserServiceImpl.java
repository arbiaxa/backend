package tn.esprit.esprobackend.services;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.entities.position;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.repositories.positionRepo;
import tn.esprit.esprobackend.repositories.userRepo;

import java.util.ArrayList;
import java.util.List;




@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService{
    private userRepo userRepository;
 private final PasswordEncoder passwordEncoder;
 private positionRepo positionRepository;
    @Override
    public List<user> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public user retrieveUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public user addUser(user u) {
       String pwd =u.getPassword();
     u.setPassword(passwordEncoder.encode(pwd));

        u.setEnabled(true);
        return userRepository.save(u);
    }

    @Override
    public void removeUser(Long userId) {
       userRepository.deleteById(userId);
    }

    @Override
    public user modifyUser(Long id,user userupdated)
    {
        user userr= retrieveUser(id);
        if (userr == null) {
            throw new RuntimeException("Utilisateur introuvable pour la mise à jour");
        }
        else {

            userr.setImg(userupdated.getImg());
            userr.setEmail(userupdated.getEmail());
            userr.setRole(userupdated.getRole());
            userr.setPassword(userupdated.getPassword());
            userr.setNameU(userupdated.getNameU());
            userr.setSurnameU(userupdated.getSurnameU());
            userr.setTelnum(userupdated.getTelnum());
            userr.setAcadmics(userupdated.getAcadmics());
            userr.setPositions(userupdated.getPositions());


            return userRepository.save(userr);
        }

    }


    public long getEnabledAccountCount(){
        List<user> users= userRepository.findByEnabled(true);

        return users.size();
    }
    public long getDisabledAccountCount()
    {
        List<user> users= userRepository.findByEnabled(false);
        return users.size();
    }


    public List<Object[]> groupPositionsByUserCountt() {
        return userRepository.groupPositionsByUserCount();
    }

    // Fonction pour mettre à jour la position de l'utilisateur
    public void updateUserPosition(long userId , long newPositionId){
        // 1. Récupérer l'utilisateur depuis la base de données
   user userr = userRepository.findById(userId).get();

        // 2. Récupérer la nouvelle position depuis la base de données
    var newPosition = positionRepository.findById(newPositionId).get();

        // 3. Mettre à jour la position de l'utilisateur
        userr.getPositions().add(newPosition);

        // 4. Enregistrer les modifications dans la base de données
      this.modifyUser(userr.getIdU(),userr);
    }




    public List<Long> retriveNotAffected ()
    {
        return userRepository.findUnassignedPositions();
    }




    public List<position> getPositionsByIds() {
        List<Long> positionIds=retriveNotAffected();
        List<position> positions = new ArrayList<>();
        for (Long id : positionIds) {
            positionRepository.findById(id).ifPresent(positions::add);
        }
        return positions;
    }



}
