/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.security;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name = "sec_menu")
public class Menu extends BaseEntity {
    
    @Column(name = "menu_action")
    private String action;

    @NotNull
    @NotEmpty
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Min(0)
    @Column(name = "menu_level", nullable = false)
    private Integer level = 0;

    @NotNull
    @Min(0)
    @Column(name = "menu_order", nullable = false)
    private Integer order = 0;

    @ManyToOne
    @JoinColumn(name = "id_parent")
    private Menu parent;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }
    
}
