package org.cancure.cpa.controller.beans;

import java.util.List;

public class PatientVisitForwardDetailsBean {

    private PatientBean patientBean;
    private List<PatientVisitForwardsBean> patientVisitForwardsBean;
    private List<PatientApprovalBean> patientApprovals;
    public PatientBean getPatientBean() {
        return patientBean;
    }
    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }
    public List<PatientVisitForwardsBean> getPatientVisitForwardsBean() {
        return patientVisitForwardsBean;
    }
    public void setPatientVisitForwardsBean(List<PatientVisitForwardsBean> patientVisitForwardsBean) {
        this.patientVisitForwardsBean = patientVisitForwardsBean;
    }
    public List<PatientApprovalBean> getPatientApprovals() {
        return patientApprovals;
    }
    public void setPatientApprovals(List<PatientApprovalBean> patientApprovals) {
        this.patientApprovals = patientApprovals;
    }
    
}
