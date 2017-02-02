package org.cancure.cpa.persistence.repository;

import java.util.List;

import org.cancure.cpa.persistence.entity.PatientBills;
import org.springframework.data.repository.CrudRepository;

public interface PatientBillRepository extends CrudRepository<PatientBills, Integer> {

	List<PatientBills> findByInvoiceId(Integer invoiceId);
	
	List<PatientBills> findByPatientVisitId(Integer patientVisitId);
}
