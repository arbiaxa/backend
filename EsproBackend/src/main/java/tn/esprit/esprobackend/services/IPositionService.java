package tn.esprit.esprobackend.services;

import tn.esprit.esprobackend.entities.position;
import tn.esprit.esprobackend.entities.user;

import java.util.List;

public interface IPositionService {

    public List<position> retrieveAllPositions();
    public position retrievePosition(Long positionId);
    public position addPosition(position p);
    public void removePosition(Long positionId);
    public position modifyPosition(Long id,position p);
}
