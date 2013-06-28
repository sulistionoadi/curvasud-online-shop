/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.dao.PermissionDao;
import id.ac.bsi.adi.ta.ecommerce.dao.RoleDao;
import id.ac.bsi.adi.ta.ecommerce.dao.UserDao;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Permission;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adi
 */

@Service("securityService")
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Autowired private UserDao userDao;
    @Autowired private RoleDao roleDao;
    @Autowired private PermissionDao permissionDao;
    
    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findUserById(String id) {
        User user = userDao.findOne(id);
        
        if(user.getRole()!=null){
            Hibernate.initialize(user.getRole().getPermissionSet());
            Hibernate.initialize(user.getRole().getMenuSet());
        }
        
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        
        if(user.getRole()!=null){
            Hibernate.initialize(user.getRole().getPermissionSet());
            Hibernate.initialize(user.getRole().getMenuSet());
        }
        
        return user;
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        Page<User> pageUser = userDao.findAll(pageable);
        
        for (User u : pageUser.getContent()) {
            if(u.getRole()!=null){
                Hibernate.initialize(u.getRole().getPermissionSet());
                Hibernate.initialize(u.getRole().getMenuSet());
            }
        }
        
        return pageUser;
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public Role findRoleById(String id) {
        Role r = roleDao.findOne(id);
        
        if(r!=null){
            Hibernate.initialize(r.getPermissionSet());
            Hibernate.initialize(r.getMenuSet());
        }
        
        return r;
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        Page<Role> pageRole = roleDao.findAll(pageable);
        
        for (Role r : pageRole.getContent()) {
            if(r!=null){
                Hibernate.initialize(r.getPermissionSet());
                Hibernate.initialize(r.getMenuSet());
            }
        }
        
        return pageRole;
    }

    @Override
    public Long countAllUsers() {
        return userDao.count();
    }

    @Override
    public Long countAllRoles() {
        return roleDao.count();
    }

    @Override
    public List<Permission> findAllPermissions() {
        return permissionDao.findAll();
    }

    @Override
    public Role findRoleByName(String name) {
        Role r = roleDao.findRoleByName(name);
        
        if(r!=null){
            Hibernate.initialize(r.getPermissionSet());
            Hibernate.initialize(r.getMenuSet());
        }
        
        return r;
    }
    
}
