package org.cancure.cpa.persistence.repository;

import java.math.BigInteger;

import org.cancure.cpa.persistence.entity.Donation;
import org.springframework.data.repository.CrudRepository;

public interface DonationRepository extends CrudRepository<Donation, BigInteger> {

	public Donation findByOrderId(String orderId);
}
