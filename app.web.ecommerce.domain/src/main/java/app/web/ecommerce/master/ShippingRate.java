/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.master;

import app.web.ecommerce.domain.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ilham-buru2@bsi
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
    @Column(name="reguler", nullable=false)
    private BigDecimal reguler;

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

    public BigDecimal getReguler() {
        return reguler;
    }

    public void setReguler(BigDecimal reguler) {
        this.reguler = reguler;
    }
    
}
