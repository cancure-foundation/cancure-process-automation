package org.cancure.cpa.controller.beans;

import java.sql.Timestamp;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

public class PatientBillsBean {

    private Integer billId;

    private String partnerBillNo;

    private Double partnerBillAmount;

    private String partnerBillPath;

    private Integer invoiceId;

    private MultipartFile partnerBillFile;
    
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

    public MultipartFile getPartnerBillFile() {
        return partnerBillFile;
    }

    public void setPartnerBillFile(MultipartFile partnerBillFile) {
        this.partnerBillFile = partnerBillFile;
    }
   
}
