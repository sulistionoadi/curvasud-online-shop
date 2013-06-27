/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.transaction;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author adi
 */
@Entity
@Table(name="trx_change_of_stock", 
        uniqueConstraints=@UniqueConstraint(columnNames={"date_of_mutation", "id_product"}))
public class ChangeOfStock extends BaseEntity {
    
    @Temporal(TemporalType.DATE)
    @Column(name="date_of_mutation")
    private Date dateOfMutation = new Date();
    
    @Column(name="initial_stock", nullable=false)
    private Integer initialStock = 0;
    
    @Column(name="final_stock", nullable=false)
    private Integer finalStock = 0;
    
    @Column(name="stock_debet", nullable=false)
    private Integer stockDebet = 0;
    
    @Column(name="stock_credit", nullable=false)
    private Integer stockCredit = 0;
    
    @ManyToOne
    @JoinColumn(name="id_product", nullable=false)
    private Product product;

    public Date getDateOfMutation() {
        return dateOfMutation;
    }

    public void setDateOfMutation(Date dateOfMutation) {
        this.dateOfMutation = dateOfMutation;
    }

    public Integer getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(Integer initialStock) {
        this.initialStock = initialStock;
    }

    public Integer getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(Integer finalStock) {
        this.finalStock = finalStock;
    }

    public Integer getStockDebet() {
        return stockDebet;
    }

    public void setStockDebet(Integer stockDebet) {
        this.stockDebet = stockDebet;
    }

    public Integer getStockCredit() {
        return stockCredit;
    }

    public void setStockCredit(Integer stockCredit) {
        this.stockCredit = stockCredit;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}
