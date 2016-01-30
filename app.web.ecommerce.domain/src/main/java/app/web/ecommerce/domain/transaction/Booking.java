/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.domain.transaction;

import app.web.ecommerce.constant.PaymentMethod;
import app.web.ecommerce.domain.master.Member;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="trx_booking")
public class Booking {
    
    @Id
    @Column(name="booking_code", nullable=false, unique=true, length=10)
    private String bookingCode;
    
    @Column(name="transaction_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="member_code", nullable=false, columnDefinition = "VARCHAR(10)")
    private Member member;
    
    @NotNull
    @Column(name="shipping_name", nullable=false, length=50)
    private String shippingName;
    
    @NotNull
    @Column(name="shipping_address", nullable=false, length=100)
    private String shippingAddress;
    
    @NotNull
    @Column(name="shipping_phone", nullable=false, length=15)
    private String shippingPhone;

    @OneToMany(mappedBy = "booking", cascade= javax.persistence.CascadeType.ALL)
    @Cascade(CascadeType.ALL)
    private List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
    
    @NotNull
    @Column(name="total_amount", nullable=false)
    private BigDecimal totalAmount;
    
    @NotNull
    @Column(name="payment_method", nullable=false, length=8)
    private PaymentMethod paymentMethod;
    
    private Integer installment;
    
    @OneToMany(mappedBy = "booking", cascade= javax.persistence.CascadeType.ALL)
    @Cascade(CascadeType.ALL)
    private List<Installment> installments = new ArrayList<Installment>();
    
    @NotNull
    @Column(name="is_approve", nullable = false)
    private Boolean approve = Boolean.FALSE;
    
    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
    
    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getInstallment() {
        return installment;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
    }

    public Boolean isApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }
    
}
