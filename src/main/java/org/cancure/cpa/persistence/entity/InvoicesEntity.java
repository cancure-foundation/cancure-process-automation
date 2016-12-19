package org.cancure.cpa.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "invoices")
public class InvoicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Timestamp date;

    private Integer pidn;

    @OneToOne(fetch = FetchType.EAGER)
    // @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "from_account_type_id")
    private AccountTypes fromAccountTypeId;

    @Column(name = "from_account_holder_id")
    private Integer fromAccountHolderId;

    @Transient
    private String fromAccountHolderName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_account_type_id")
    private AccountTypes toAccountTypeId;

    @Column(name = "to_account_holder_id")
    private Integer toAccountHolderId;

    private Double amount;

    private String status;

    @Column(name = "closed_date")
    private Timestamp closedDate;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFromAccountHolderName() {
        return fromAccountHolderName;
    }

    public void setFromAccountHolderName(String fromAccountHolderName) {
        this.fromAccountHolderName = fromAccountHolderName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getPidn() {
        return pidn;
    }

    public void setPidn(Integer pidn) {
        this.pidn = pidn;
    }

    public AccountTypes getFromAccountTypeId() {
        return fromAccountTypeId;
    }

    public void setFromAccountTypeId(AccountTypes fromAccountTypeId) {
        this.fromAccountTypeId = fromAccountTypeId;
    }

    public Integer getFromAccountHolderId() {
        return fromAccountHolderId;
    }

    public void setFromAccountHolderId(Integer fromAccountHolderId) {
        this.fromAccountHolderId = fromAccountHolderId;
    }

    public AccountTypes getToAccountTypeId() {
        return toAccountTypeId;
    }

    public void setToAccountTypeId(AccountTypes toAccountTypeId) {
        this.toAccountTypeId = toAccountTypeId;
    }

    public Integer getToAccountHolderId() {
        return toAccountHolderId;
    }

    public void setToAccountHolderId(Integer toAccountHolderId) {
        this.toAccountHolderId = toAccountHolderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Timestamp closedDate) {
        this.closedDate = closedDate;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

}
