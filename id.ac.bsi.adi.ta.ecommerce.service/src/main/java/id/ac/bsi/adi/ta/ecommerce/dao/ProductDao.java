/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface ProductDao extends PagingAndSortingRepository<Product, String> {
    
    Product findProductByProductCode(String code);
    
}
