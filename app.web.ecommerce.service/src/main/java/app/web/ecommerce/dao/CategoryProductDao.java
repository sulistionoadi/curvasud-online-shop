/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.domain.master.CategoryProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface CategoryProductDao extends PagingAndSortingRepository<CategoryProduct, String> {

    CategoryProduct findCategoryProductByCategoryCode(String categoryCode);
    
}
