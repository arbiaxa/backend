package tn.esprit.esprobackend.services;

import tn.esprit.esprobackend.entities.academicSp;


import java.util.List;

public interface IAcademicService {

    public List<academicSp> retrieveAllAcademics();
    public academicSp retrieveAcademic(Long acadamicId);
    public academicSp addAcademic(academicSp ac);
    public void removeAcademic(Long acadamicId);
    public academicSp modifyAcademic(Long id,academicSp ac);
}
