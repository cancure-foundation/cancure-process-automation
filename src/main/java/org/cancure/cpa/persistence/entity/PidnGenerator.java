package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pidn_generator")
public class PidnGenerator {

	@Column(name="prn",unique = true, nullable = false)
    private Integer prn;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pidn;

	public Integer getPrn() {
		return prn;
	}

	public void setPrn(Integer prn) {
		this.prn = prn;
	}

	public Integer getPidn() {
		return pidn;
	}

	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}
    
    
}
