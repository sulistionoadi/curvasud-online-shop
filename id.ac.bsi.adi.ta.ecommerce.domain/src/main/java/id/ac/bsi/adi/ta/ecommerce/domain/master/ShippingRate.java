/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.master;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author adi
 */

@Entity
@Table(name="mst_shipping_rate")
public class ShippingRate extends BaseEntity {
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_city", nullable=false)
    private City city;
    
    @NotNull
    @Column(name="express", nullable=false)
    private BigDecimal express;
    
    @NotNull
    @Column(name="regular", nullable=false)
    private BigDecimal regular;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public BigDecimal getExpress() {
        return express;
    }

    public void setExpress(BigDecimal express) {
        this.express = express;
    }

    public BigDecimal getRegular() {
        return regular;
    }

    public void setRegular(BigDecimal regular) {
        this.regular = regular;
    }
    
}
