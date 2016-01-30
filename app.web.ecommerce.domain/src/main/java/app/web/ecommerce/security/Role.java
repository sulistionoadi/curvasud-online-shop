/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.security;

import app.web.ecommerce.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="sec_role")
public class Role extends BaseEntity {
    
    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true, length=20)
    private String name;
    
    @Column(length=100)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "sec_role_permission",
            joinColumns = @JoinColumn(name = "id_role", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_permission", nullable = false)
    )
    private Set<Permission> permissionSet = new HashSet<Permission>();

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

    @Override
    public String toString() {
        return "Role{" + "name=" + name + ", description=" + description + '}';
    }
}
