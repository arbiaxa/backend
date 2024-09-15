package tn.esprit.esprobackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.services.IUserService;

import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;

// http://localhost:8089/tpfoyer/chambre/retrieve-all-chambres
@RestController
@RequestMapping("/admin")
//admin role is needed tos acces the endpoint
//@PreAuthorize("hasRole('ROLE_ADMIN')")

@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "http://localhost:4200")
//@PreAuthorize("hasRole('ADMIN')")
public class adminRestController {

    IUserService userService;



    @PutMapping("/updateUserPos/{userId}/{positionId}")
    public ResponseEntity<String> updateUserPosition(@PathVariable long userId, @PathVariable long positionId) {
        try {
            userService.updateUserPosition(userId, positionId);
            return ResponseEntity.ok("Position de l'utilisateur mise à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de la mise à jour de la position de l'utilisateur.");
        }
    }


    @GetMapping("/retrieve-all-users")

    //  @Secured({"ROLE_ADMIN"})
    public List<user> getUsers() {
        List<user> listChambres = userService.retrieveAllUsers();
        return listChambres;
    }



    @GetMapping("/notassa")
    public ResponseEntity <? > notAffectedPos() {
        //   return userService.retriveNotAffected();
        return ResponseEntity.ok( userService.retriveNotAffected());
    }



    @GetMapping("/notassaa")
    public ResponseEntity <? > notAffectedPosList(){
        //   return userService.retriveNotAffected();
        return ResponseEntity.ok( userService.getPositionsByIds());
    }




    @GetMapping("/retrieve-user/{user-id}")
    public user retrieveChambre(@PathVariable("user-id") Long chId) {
        user userr = userService.retrieveUser(chId);
        return userr;
    }


    @PostMapping("/add-user")
    public user addChambre(@RequestBody user u) {
        user userr = userService.addUser(u);
        return userr;
    }
    // http://localhost:8089/tpfoyer/chambre/remove-chambre/{chambre-id}
    @DeleteMapping("/remove-user/{user-id}")
    public void removeUser(@PathVariable("user-id") Long chId) {
        userService.removeUser(chId);
    }
    // http://localhost:8089/tpfoyer/chambre/modify-chambre



    @PutMapping("/update-user/{userId}")
    public ResponseEntity<user> updateUser(@PathVariable Long userId, @RequestBody user updatedUser) {

        updatedUser.setIdU(userId);
        user updatedUserResult = userService.modifyUser(userId,updatedUser);

        return ResponseEntity.ok(updatedUserResult);
    }



    @GetMapping("/enabled-count")
    public ResponseEntity<Long> getEnabledAccountCount() {
        long enabledCount = userService.getEnabledAccountCount();
        return ResponseEntity.ok(enabledCount);
    }

    @GetMapping("/disabled-count")
    public ResponseEntity<Long> getDisabledAccountCount() {
        long disabledCount = userService.getDisabledAccountCount();
        return ResponseEntity.ok(disabledCount);
    }



    @GetMapping("/groupingPosByUser")
    public List<Object[]> groupPositionsByUserCount() {
        return userService.groupPositionsByUserCountt();
    }


  /* public ResponseEntity<List<user>> getUsers() {
        List<user> liste=userService.retrieveAllUsers();
        if(liste.size()==0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        else
             return ResponseEntity.ok(liste);

    }*/

      /* @PutMapping("/modify-chambre/{user-id}")
    public user modifyChambre(@PathVariable("user-id") Long id,@RequestBody user c) {
        user userr = userService.modifyUser(id,c);
        return userr;
    }
*/


}
