/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adi
 */
public interface PermissionDao extends JpaRepository<Permission, String> {
    
}
