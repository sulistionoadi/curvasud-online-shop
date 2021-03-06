/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.transaction;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author adi
 */

@Entity
@Table(name="trx_booking_detail",
        uniqueConstraints=@UniqueConstraint(columnNames={"id_booking", "id_product"}))
public class BookingDetail extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name="id_booking", nullable=false)
    private Booking booking;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_product", nullable=false)
    private Product product;
    
    @NotNull
    @Min(value=1)
    @Column(name="qty", nullable=false)
    private Integer qty;
    
    @NotNull
    @Column(name="amount", nullable=false)
    private BigDecimal amount;
    
    @NotNull
    @Column(name="total_amount", nullable=false)
    private BigDecimal totalAmount;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
