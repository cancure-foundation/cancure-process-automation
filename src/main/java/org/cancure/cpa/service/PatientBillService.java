package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientBills;

public interface PatientBillService {

    String savePatientBills(List<PatientBills> patientBills);
    
    PatientBills getPatientBills(Integer billId);

	List<PatientBills> getPatientBillByInvoice(Integer invoiceId);

    List<PatientBills> getPatientBillByPatientVisitId(Integer patientVisitId);
}
