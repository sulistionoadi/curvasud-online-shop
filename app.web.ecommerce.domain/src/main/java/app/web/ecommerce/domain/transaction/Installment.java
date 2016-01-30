/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.web.ecommerce.domain.transaction;

import app.web.ecommerce.domain.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="trx_installment",
        uniqueConstraints=@UniqueConstraint(columnNames={"booking_code", "installment_no"}))
public class Installment extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name="booking_code", nullable=false, columnDefinition = "VARCHAR(10)")
    private Booking booking;
    
    @NotNull
    @Column(name="installment_no", nullable = false)
    private Integer installmentNo;
    
    @NotNull
    @Column(name="percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;
    
    @NotNull
    @Column(name="already_invoiced", nullable = false)
    private Boolean invoiced = Boolean.FALSE;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Integer installmentNo) {
        this.installmentNo = installmentNo;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Boolean invoiced) {
        this.invoiced = invoiced;
    }
    
}
