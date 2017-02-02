package org.cancure.cpa.service;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientBills;
import org.cancure.cpa.persistence.repository.PatientBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientBillServiceImpl implements PatientBillService {

    @Autowired
    PatientBillRepository patientBillRepo;
    
    @Override
    public String savePatientBills(List<PatientBills> patientBills) {
        // TODO Auto-generated method stub
        for(PatientBills patientBill:patientBills){
            patientBillRepo.save(patientBill);
        }
        return "success";
    }

    @Override
    public PatientBills getPatientBills(Integer billId) {
        // TODO Auto-generated method stub
        return patientBillRepo.findOne(billId);
    }

    @Override
    public List<PatientBills> getPatientBillByInvoice(Integer invoiceId) {
        // TODO Auto-generated method stub
        return patientBillRepo.findByInvoiceId(invoiceId);
    }
    
    @Override
    public List<PatientBills> getPatientBillByPatientVisitId(Integer patientVisitId) {
        // TODO Auto-generated method stub
        return patientBillRepo.findByPatientVisitId(patientVisitId);
    }
}
