/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.master.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface SupplierDao extends PagingAndSortingRepository<Supplier, String>, JpaRepository<Supplier, String> {
    
    Supplier findSupplierByCode(String code);
    
}
