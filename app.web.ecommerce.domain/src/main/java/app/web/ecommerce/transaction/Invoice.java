/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.transaction;

import app.web.ecommerce.domain.BaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="trx_invoice")
public class Invoice {
    
    @Id
    @Column(name="invoice_number", length=10, nullable=false, unique=true)
    private String invoiceNumber;
    
    @NotNull
    @OneToOne 
    @JoinColumn(name="id_booking")
    private Booking booking;
    
    @Column(name="transaction_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
}
