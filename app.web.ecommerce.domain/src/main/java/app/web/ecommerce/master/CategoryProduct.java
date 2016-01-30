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
 * @author adi
 */

@Entity
@Table(name="mst_category_product")
public class CategoryProduct {
    
    @Id @NotNull @NotEmpty
    @Column(name="category_code", length=3)
    private String categoryCode;
    
    @NotNull @NotEmpty
    @Column(nullable=false, length=100)
    private String description;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
