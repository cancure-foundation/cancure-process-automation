package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="list_value")
public class ListValues {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="list_value_id")
	private String listValueId; 
	
	@Column(name="list_id")
    private Integer listId;

	@NotEmpty
	private String value;
	
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getListValueId() {
		return listValueId;
	}

	public void setListValueId(String listValueId) {
		this.listValueId = listValueId;
	}
	
}
