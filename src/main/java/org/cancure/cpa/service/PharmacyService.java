package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Hospital;
import org.cancure.cpa.persistence.entity.Pharmacy;

public interface PharmacyService {

	Pharmacy getPharmacy(Integer pharmacyId);

	Iterable<Pharmacy> listPharmacies();

	Pharmacy savePharmacy(Pharmacy pharmacy);

}
