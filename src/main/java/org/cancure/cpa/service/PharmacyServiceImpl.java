package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.Pharmacy;
import org.cancure.cpa.persistence.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("pharmacyService")
public class PharmacyServiceImpl implements PharmacyService {
	
	@Override
	public Pharmacy savePharmacy(Pharmacy pharmacy) {
		if (pharmacy.getPharmacyId()== null) {
			pharmacy.setEnabled(true);
		}
		return pharmacyRepo.save(pharmacy);
	}
	

	@Autowired
	PharmacyRepository pharmacyRepo;
	
	@Override
	public Iterable<Pharmacy> listPharmacies() {
		return pharmacyRepo.findAllActive();
	}

	@Override
	public Pharmacy gePharmacy(Integer id) {
		return pharmacyRepo.findOne(id);
	}
}
