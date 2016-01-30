/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.transaction;

import app.web.ecommerce.domain.BaseEntity;
import app.web.ecommerce.master.Product;
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
@Table(name="trx_purchase_detail",uniqueConstraints=@UniqueConstraint(columnNames={"id_purchase", "id_product"}))
public class PurchaseDetail extends BaseEntity{
    
    @ManyToOne
    @JoinColumn(name="id_purchase", nullable=false)
    private Purchase purchase;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_product", nullable=false)
    private Product product;
    
    @NotNull @Min(1)
    @Column(name="qty", nullable=false)
    private Integer quantity = 0;
    
    @NotNull
    @Column(name="price", nullable=false)
    private BigDecimal price = BigDecimal.ZERO;
    
    @NotNull
    @Column(name="total_price", nullable=false)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
