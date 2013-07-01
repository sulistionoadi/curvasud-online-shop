/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
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
    
    @Query("select count(p) from Purchase p where p.purchaseDate between :start and :end")
    public Long countByPurchaseDateBetween(Date start, Date end);
    public Page<Purchase> findByPurchaseDateBetween(@Param("start") Date start, @Param("end") Date end, Pageable pageable);
    public List<Purchase> findByPurchaseNumber(String purchaseNumber);
    
}
