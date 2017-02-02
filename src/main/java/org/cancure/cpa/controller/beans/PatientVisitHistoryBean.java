package org.cancure.cpa.controller.beans;

import java.util.List;
import java.util.Map;

import org.cancure.cpa.persistence.entity.PatientVisitDocuments;

public class PatientVisitHistoryBean {

    private Map<String, String> task;

    private PatientBean patientBean;

    private PatientVisitBean patientVisit;

    private Boolean workflowExists;

    List<PatientApprovalBean> patientApprovals;

    List<InvoicesBean> invoicesList;

    List<PatientVisitDocumentBean> patientVisitDocuments;

    private String topupComments;

    private Double topupEstimateAmount;
    
    List<PatientBillsBean> patientBills;

    public Boolean getWorkflowExists() {
        return workflowExists;
    }

    public void setWorkflowExists(Boolean workflowExists) {
        this.workflowExists = workflowExists;
    }

    public Map<String, String> getTask() {
        return task;
    }

    public void setTask(Map<String, String> task) {
        this.task = task;
    }

    public PatientVisitBean getPatientVisit() {
        return patientVisit;
    }

    public void setPatientVisit(PatientVisitBean patientVisit) {
        this.patientVisit = patientVisit;
    }

    public PatientBean getPatientBean() {
        return patientBean;
    }

    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }

    public List<PatientApprovalBean> getPatientApprovals() {
        return patientApprovals;
    }

    public void setPatientApprovals(List<PatientApprovalBean> patientApprovals) {
        this.patientApprovals = patientApprovals;
    }

    public List<InvoicesBean> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<InvoicesBean> invoicesList) {
        this.invoicesList = invoicesList;
    }

    public List<PatientVisitDocumentBean> getPatientVisitDocuments() {
        return patientVisitDocuments;
    }

    public void setPatientVisitDocuments(List<PatientVisitDocumentBean> patientVisitDocuments) {
        this.patientVisitDocuments = patientVisitDocuments;
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

    public List<PatientBillsBean> getPatientBills() {
        return patientBills;
    }

    public void setPatientBills(List<PatientBillsBean> patientBills) {
        this.patientBills = patientBills;
    }
    
}
