/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.transaction;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="trx_payment")
public class Payment extends BaseEntity {
    
    @Column(name="payment_code", length=10, nullable=false)
    private String paymentCode;
    
    @NotNull
    @Column(name="payment_date", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date paymentDate = new Date();
    
    @Column(name="approve_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approveDate;
    
    @Column(name="transaction_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_booking", nullable=false)
    private Booking booking;
    
    @NotNull
    @Column(name="transfer_amount", nullable=false)
    private BigDecimal transferAmount;
    
    @NotNull @NotEmpty
    @Column(name="account_name", nullable=false, length=35)
    private String accountName;
    
    @Column(name="approved", nullable=false)
    private Boolean approved = Boolean.FALSE;

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
}
