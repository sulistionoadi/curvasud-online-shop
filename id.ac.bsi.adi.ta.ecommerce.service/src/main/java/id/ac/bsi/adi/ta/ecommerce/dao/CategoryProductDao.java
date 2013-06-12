/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface CategoryProductDao extends PagingAndSortingRepository<CategoryProduct, String> {
    
    CategoryProduct findCategoryProductByCategoryCode(String categoryCode);
    
}
