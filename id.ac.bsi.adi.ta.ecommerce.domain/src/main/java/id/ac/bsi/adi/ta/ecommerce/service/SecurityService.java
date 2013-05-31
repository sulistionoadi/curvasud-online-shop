/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Permission;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import java.util.List;

/**
 *
 * @author adi
 */
public interface SecurityService {
    
    public void save(User user);
    public void delete(User user);
    public User findUserById(String id);
    public User findUserByUsername(String username);
    public Long countAllUsers();
    public List<User> findAllUsers(Integer start, Integer rows);
    
    public void save(Role role);
    public void delete(Role role);
    public Role findRoleById(String id);
    public Long countAllRoles();
    public List<Role> findAllRoles(Integer start, Integer rows);
    
    public List<Permission> findAllPermissions();
    
}
