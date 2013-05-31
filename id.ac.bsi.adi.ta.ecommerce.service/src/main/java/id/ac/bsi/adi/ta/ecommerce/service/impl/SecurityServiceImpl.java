/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.domain.security.Permission;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adi
 */

@Service("securityService")
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public User findUserById(String id) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
        
        if(user.getRole()!=null){
            Hibernate.initialize(user.getRole().getPermissionSet());
            Hibernate.initialize(user.getRole().getMenuSet());
        }
        
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = (User) sessionFactory.getCurrentSession()
                .createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .uniqueResult();
        
        if(user.getRole()!=null){
            Hibernate.initialize(user.getRole().getPermissionSet());
            Hibernate.initialize(user.getRole().getMenuSet());
        }
        
        return user;
    }

    @Override
    public List<User> findAllUsers(Integer start, Integer rows) {
        if(start==null) start=0;
        if(rows==null) rows=20;
        
        List<User> users = sessionFactory.getCurrentSession()
                .createQuery("from User u order by u.username")
                .setFirstResult(start)
                .setMaxResults(rows)
                .list();
        
        for (User u : users) {
            if(u.getRole()!=null){
                Hibernate.initialize(u.getRole().getPermissionSet());
                Hibernate.initialize(u.getRole().getMenuSet());
            }
        }
        
        return users;
    }

    @Override
    public void save(Role role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
    }

    @Override
    public void delete(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    @Override
    public Role findRoleById(String id) {
        Role r = (Role) sessionFactory.getCurrentSession().get(Role.class, id);
        
        if(r!=null){
            Hibernate.initialize(r.getPermissionSet());
            Hibernate.initialize(r.getMenuSet());
        }
        
        return r;
    }

    @Override
    public List<Role> findAllRoles(Integer start, Integer rows) {
        if(start==null) start=0;
        if(rows==null) rows=20;
        
        List<Role> roles = sessionFactory.getCurrentSession()
                .createQuery("from Role r order by r.name")
                .setFirstResult(start)
                .setMaxResults(rows)
                .list();
        
        for (Role r : roles) {
            if(r!=null){
                Hibernate.initialize(r.getPermissionSet());
                Hibernate.initialize(r.getMenuSet());
            }
        }
        
        return roles;
    }

    @Override
    public Long countAllUsers() {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(u) from User u").uniqueResult();
    }

    @Override
    public Long countAllRoles() {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(r) from Role r").uniqueResult();
    }

    @Override
    public List<Permission> findAllPermissions() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Permission p order by p.label")
                .list();
    }
    
}
