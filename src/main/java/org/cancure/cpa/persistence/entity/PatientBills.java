package org.cancure.cpa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "patient_bills")
public class PatientBills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bill_id")
    private Integer billId;
    
    @Column(name="partner_bill_no")
    private String partnerBillNo;
    
    @Column(name="partner_bill_amount")
    private Double partnerBillAmount;
    
    @Column(name="partner_bill_path")
    private String partnerBillPath;
    
    @Column(name="invoice_id")
    private Integer invoiceId;

    @Column(name="patient_visit_id")
    private Integer patientVisitId;
    
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getPartnerBillNo() {
        return partnerBillNo;
    }

    public void setPartnerBillNo(String partnerBillNo) {
        this.partnerBillNo = partnerBillNo;
    }

    public Double getPartnerBillAmount() {
        return partnerBillAmount;
    }

    public void setPartnerBillAmount(Double partnerBillAmount) {
        this.partnerBillAmount = partnerBillAmount;
    }

    public String getPartnerBillPath() {
        return partnerBillPath;
    }

    public void setPartnerBillPath(String partnerBillPath) {
        this.partnerBillPath = partnerBillPath;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getPatientVisitId() {
        return patientVisitId;
    }

    public void setPatientVisitId(Integer patientVisitId) {
        this.patientVisitId = patientVisitId;
    }
}
