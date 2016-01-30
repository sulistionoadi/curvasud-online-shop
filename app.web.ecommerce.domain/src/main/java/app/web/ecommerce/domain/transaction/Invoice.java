/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.domain.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Invoice implements Serializable {
    
    @Id
    @Column(name="invoice_number", length=10, nullable=false, unique=true)
    private String invoiceNumber;
    
    @NotNull
    @ManyToOne 
    @JoinColumn(name="id_installment", nullable = false, columnDefinition = "VARCHAR(36)")
    private Installment installment;
    
    @Column(name="transaction_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    
    @NotNull
    @Column(name="invoice_amount", nullable=false, scale = 2, precision = 16)
    private BigDecimal invoiceAmount;

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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
