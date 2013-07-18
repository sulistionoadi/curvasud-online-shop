/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.ChangeOfStock;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author adi
 */
public interface ChangeOfStockDao extends PagingAndSortingRepository<ChangeOfStock, String> {
    
    @Query("select cos from ChangeOfStock cos where cos.product.id = :product and month(dateOfMutation) = month(:tanggal) and year(dateOfMutation) = year(:tanggal)")
    public ChangeOfStock getByProductAndPeriode(@Param("product") String product, @Param("tanggal") Date periode);
    
}
