/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author adi
 */
public interface ProductDao extends PagingAndSortingRepository<Product, String>, JpaRepository<Product, String> {
    
    @Query("select count(p) from Product p where p.category = :cat")
    public Long countByCategory(@Param("cat") CategoryProduct categoryProduct);
    
    @Query("select count(p) from Product p where p.category.description like :key or p.productName like :key or p.productInfo like :key")
    public Long countByKeyword(@Param("key") String keyword);
    
    public Page<Product> findByCategory(CategoryProduct categoryProduct, Pageable pageable);
    
    @Query("select p from Product p where p.category.description like :key or p.productName like :key or p.productInfo like :key")
    public Page<Product> findByKeyword(@Param("key") String keyword, Pageable pageable);
    
    Product findProductByProductCode(String code);
    
}
