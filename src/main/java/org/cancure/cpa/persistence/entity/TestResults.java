package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestResults {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name="test_result_id")
private Integer id;
private String comment;
private String docPath;
private Integer refId;

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public String getDocPath() {
	return docPath;
}
public void setDocPath(String docPath) {
	this.docPath = docPath;
}
public Integer getRefId() {
	return refId;
}
public void setRefId(Integer refId) {
	this.refId = refId;
}

}
