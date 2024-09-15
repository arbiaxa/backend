package tn.esprit.esprobackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprobackend.entities.position;
import tn.esprit.esprobackend.services.IPositionService;

import java.util.List;
@RestController
@RequestMapping("/pos")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN') ")
public class positionRestController {
    IPositionService posService;



    @GetMapping("/retrieve-all-positions")
    public List<position> getPositions() {
        List<position> listPos = posService.retrieveAllPositions();
        return listPos;
    }

   /* public ResponseEntity<List<user>> getUsers() {
        List<user> liste=userService.retrieveAllUsers();
        if(liste.size()==0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        else
             return ResponseEntity.ok(liste);

    }*/




    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")

    @GetMapping("/retrieve-pos/{user-id}")
    public position retrievePosition(@PathVariable("user-id") Long pId) {
        position pos  = posService.retrievePosition(pId);
        return pos;
    }


    @PostMapping("/add-pos")
    public position addPosistion(@RequestBody position p) {
        position pos= posService.addPosition(p);
        return pos;
    }
    // http://localhost:8089/tpfoyer/chambre/remove-chambre/{chambre-id}
    @DeleteMapping("/remove-pos/{pos-id}")
    public void removePosition(@PathVariable("pos-id") Long pId) {
        posService.removePosition(pId);
    }
    // http://localhost:8089/tpfoyer/chambre/modify-chambre

   /* @PutMapping("/modify-chambre/{user-id}")
    public user modifyChambre(@PathVariable("user-id") long id,@RequestBody user c) {
        user userr = userService.modifyUser(id,c);
        return userr;
    }
*/

    @PutMapping("/updatePos/{posId}")
    public ResponseEntity<position> updatePosition(@PathVariable Long posId, @RequestBody position updatedPos) {

        updatedPos.setIdP(posId);

        position updatedUserResult = posService.modifyPosition(posId,updatedPos);
        if (updatedPos == null) {
            // Si la position n'a pas été trouvée, retournez une réponse avec le statut 404 NOT FOUND
            return ResponseEntity.notFound().build();
        } else {
            // Si la position a été mise à jour avec succès, retournez une réponse avec le statut 200 OK et les données de la position mise à jour
            return ResponseEntity.ok(updatedPos);

        }
    }





}
