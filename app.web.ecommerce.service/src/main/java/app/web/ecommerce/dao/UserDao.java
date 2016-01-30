/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.security.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface UserDao extends PagingAndSortingRepository<User, String> {
    
    User findUserByUsername(String username);
    
}
