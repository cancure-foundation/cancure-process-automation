package org.cancure.cpa.service;

import java.io.IOException;
import java.util.List;

import org.cancure.cpa.controller.beans.PatientDocumentBean;
import org.cancure.cpa.controller.beans.PatientInvestigationBean;

public interface PatientInvestigationService {

    String savePatientInvestigation(PatientInvestigationBean patientInvestigationBean,String status) throws IOException;

    String savePatientDocument(List<PatientDocumentBean> patientDocumentBeans) throws IOException;

    String savePatientExamination(PatientInvestigationBean patientInvestigationBean,
            List<PatientDocumentBean> patientDocumentBean) throws IOException;
}
