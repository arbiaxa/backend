package tn.esprit.esprobackend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.entities.position;
import tn.esprit.esprobackend.repositories.positionRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class PositionServiceImpl implements IPositionService{
    positionRepo positionRepository;
    @Override
    public List<position> retrieveAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public position retrievePosition(Long positionId) {
        return positionRepository.findById(positionId).get();
    }

    @Override
    public position addPosition(position p) {
        return positionRepository.save(p);
    }

    @Override
    public void removePosition(Long positionId) {
        positionRepository.deleteById(positionId);
    }

    @Override
    public position modifyPosition(Long id,position p) {

        position pos= retrievePosition(id);
        if (pos == null) {
            throw new RuntimeException("Position introuvable pour la mise à jour");
        }
        else {

            pos.setNameP(p.getNameP());
            pos.setShiftHours(p.getShiftHours());

          return positionRepository.save(pos);
        }
    }



}
