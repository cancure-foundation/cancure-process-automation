package org.cancure.cpa.persistence.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="list")
public class ListOfValues {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="list_id")
    private Integer listId;

	@NotEmpty
	private String name;

	@OneToMany (mappedBy = "listId")
	private Set<ListValues> listValues;
	
	public Set<ListValues> getListValues() {
		return listValues;
	}

	public void setListValues(Set<ListValues> listValues) {
		this.listValues = listValues;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
