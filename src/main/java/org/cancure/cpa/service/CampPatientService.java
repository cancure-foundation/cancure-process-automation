package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.controller.beans.CampPatientBean;
import org.cancure.cpa.persistence.entity.CampPatient;

public interface CampPatientService {

    CampPatient saveCampPatient(CampPatient patientcamp);

    Iterable<CampPatient> listCampPatient();

    List<CampPatientBean> getPatientByCamp(Integer campId);
}
