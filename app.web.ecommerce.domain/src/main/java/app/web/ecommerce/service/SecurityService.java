/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service;

import app.web.ecommerce.domain.security.Permission;
import app.web.ecommerce.domain.security.Role;
import app.web.ecommerce.domain.security.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface SecurityService {
    
    public User save(User user);
    public void delete(User user);
    public User findUserById(String id);
    public User findUserByUsername(String username);
    public Long countAllUsers();
    public Page<User> findAllUsers(Pageable pageable);
    
    public Role save(Role role);
    public void delete(Role role);
    public Role findRoleById(String id);
    public Role findRoleByName(String name);
    public Long countAllRoles();
    public Page<Role> findAllRoles(Pageable pageable);
    public List<Role> findAllRoles();
    
    public List<Permission> findAllPermissions();
    
}
