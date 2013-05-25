/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.security;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="sec_permission")
public class Role extends BaseEntity {
    
    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "sec_role_permission",
            joinColumns = @JoinColumn(name = "id_role", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_permission", nullable = false)
    )
    private Set<Permission> permissionSet = new HashSet<Permission>();
    
    @ManyToMany
    @JoinTable(
            name = "sec_role_menu",
            joinColumns = @JoinColumn(name = "id_role", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_menu", nullable = false)
    )
    @OrderBy(value = "level,order")
    private Set<Menu> menuSet = new TreeSet<Menu>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }
    
}
