package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;
import java.util.List;

public class PatientVisitBean {

	private Long id;
	private Integer pidn;
	private Timestamp date;
	private String accountTypeId;
	private String accountHolderId;
	private String taskId;
	private String status;
	private String topupNeeded;
	private List<PatientVisitDocumentBean> patientHospitalVisitDocumentBeanList;
	private List<PatientVisitForwardsBean> forwardList;
	private List<PatientBillsBean> patientBills;
	private String comments;
	private Double amount;
	private String topupComments;
	private Double topupEstimateAmount;
	
	public String getTopupNeeded() {
		return topupNeeded;
	}
	public void setTopupNeeded(String topupNeeded) {
		this.topupNeeded = topupNeeded;
	}
	
	public List<PatientVisitDocumentBean> getPatientHospitalVisitDocumentBeanList() {
		return patientHospitalVisitDocumentBeanList;
	}
	public void setPatientHospitalVisitDocumentBeanList(
			List<PatientVisitDocumentBean> patientHospitalVisitDocumentBeanList) {
		this.patientHospitalVisitDocumentBeanList = patientHospitalVisitDocumentBeanList;
	}
	
	public List<PatientVisitForwardsBean> getForwardList() {
		return forwardList;
	}
	public void setForwardList(List<PatientVisitForwardsBean> forwardList) {
		this.forwardList = forwardList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPidn() {
		return pidn;
	}
	public void setPidn(Integer pidn) {
		this.pidn = pidn;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getAccountHolderId() {
		return accountHolderId;
	}
	public void setAccountHolderId(String accountHolderId) {
		this.accountHolderId = accountHolderId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    public List<PatientBillsBean> getPatientBills() {
        return patientBills;
    }
    public void setPatientBills(List<PatientBillsBean> patientBills) {
        this.patientBills = patientBills;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getTopupComments() {
        return topupComments;
    }
    public void setTopupComments(String topupComments) {
        this.topupComments = topupComments;
    }
    public Double getTopupEstimateAmount() {
        return topupEstimateAmount;
    }
    public void setTopupEstimateAmount(Double topupEstimateAmount) {
        this.topupEstimateAmount = topupEstimateAmount;
    }
   
    
}
