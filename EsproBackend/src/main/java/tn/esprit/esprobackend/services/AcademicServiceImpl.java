package tn.esprit.esprobackend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.entities.academicSp;
import tn.esprit.esprobackend.repositories.acadamicRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class AcademicServiceImpl implements IAcademicService{
    acadamicRepo academicRepository;
    @Override
    public List<academicSp> retrieveAllAcademics() {
        return academicRepository.findAll();
    }

    @Override
    public academicSp retrieveAcademic(Long acadamicId) {
        return academicRepository.findById(acadamicId).get();
    }

    @Override
    public academicSp addAcademic(academicSp ac) {
        return academicRepository.save(ac);
    }

    @Override
    public void removeAcademic(Long acadamicId) {
        academicRepository.deleteById(acadamicId);
    }

    @Override
    public academicSp modifyAcademic(Long id,academicSp ac) {

        academicSp oldAc = retrieveAcademic(id);
        if (oldAc == null) {
            throw new RuntimeException("Academic introuvable pour la mise à jour");

        }
        else {
            oldAc.setNameAS(ac.getNameAS());
            oldAc.setUsers(ac.getUsers());

            return academicRepository.save(oldAc);
        }


    }


}
