/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface RoleDao extends PagingAndSortingRepository<Role, String>, JpaRepository<Role, String> {
    
    Role findRoleByName(String name);
}
