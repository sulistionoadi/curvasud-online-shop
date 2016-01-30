/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.security;

import app.web.ecommerce.domain.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="sec_permission")
public class Permission extends BaseEntity {
    
    @NotNull
    @NotEmpty
    @Column(name = "label", nullable = false, unique = true, length=30)
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Permission{" + "label=" + label + '}';
    }
    
}
