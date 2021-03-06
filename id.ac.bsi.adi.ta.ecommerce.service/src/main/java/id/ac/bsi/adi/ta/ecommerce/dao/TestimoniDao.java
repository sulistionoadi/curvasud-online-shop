/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Testimoni;
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
public interface TestimoniDao extends PagingAndSortingRepository<Testimoni, String>, JpaRepository<Testimoni, String>{
    
    @Query("select count(t) from Testimoni t where t.product = :product")
    public Long countByProduct(@Param("product") Product product);
    
    public Page<Testimoni> findByProduct(Product product, Pageable pageable);
    
}
