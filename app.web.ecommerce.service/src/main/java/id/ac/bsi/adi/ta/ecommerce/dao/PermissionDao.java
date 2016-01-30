/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adi
 */
public interface PermissionDao extends JpaRepository<Permission, String> {
    
}
