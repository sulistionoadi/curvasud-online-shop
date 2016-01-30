/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.master;

import app.web.ecommerce.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="mst_city")
public class City {
    
    @Id @NotNull @NotEmpty
    @Column(name="city_code", length=3)
    private String code;
    
    @NotNull @NotEmpty
    @Column(name="city_name", nullable=false, length=25)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}