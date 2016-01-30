/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.transaction.ChangeOfStock;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author adi
 */
public interface ChangeOfStockDao extends PagingAndSortingRepository<ChangeOfStock, String> {
    
    @Query("select cos from ChangeOfStock cos where cos.product.productCode = :kode and month(dateOfMutation) = month(:tanggal) and year(dateOfMutation) = year(:tanggal)")
    public ChangeOfStock getByProductAndPeriode(@Param("kode") String kode, @Param("tanggal") Date periode);
    
    @Query("select count(cos) from ChangeOfStock cos where month(dateOfMutation) = month(:tanggal) and year(dateOfMutation) = year(:tanggal)")
    public Long countByPeriode(@Param("tanggal") Date Periode);
    
    @Query("select cos from ChangeOfStock cos where month(dateOfMutation) = month(:tanggal) and year(dateOfMutation) = year(:tanggal)")
    public Page<ChangeOfStock> findByPeriode(@Param("tanggal") Date Periode, Pageable pageable);
    
}
