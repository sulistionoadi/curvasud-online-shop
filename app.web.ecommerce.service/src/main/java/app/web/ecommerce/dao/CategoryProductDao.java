/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.master.CategoryProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface CategoryProductDao extends PagingAndSortingRepository<CategoryProduct, String> {

    CategoryProduct findCategoryProductByCategoryCode(String categoryCode);
    
}
