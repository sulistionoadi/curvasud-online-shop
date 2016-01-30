/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.transaction.Purchase;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author adi
 */
public interface PurchaseDao extends PagingAndSortingRepository<Purchase, String> {
    
    @Query("select count(p) from Purchase p where date(p.purchaseDate) between :start and :end")
    public Long countByPurchaseDateBetween(@Param("start") Date start, @Param("end") Date end);
    
    public Page<Purchase> findByPurchaseDateBetween(Date start, Date end, Pageable pageable);
    public List<Purchase> findByPurchaseNumber(String purchaseNumber);
    
}
