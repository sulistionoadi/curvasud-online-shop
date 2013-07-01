/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author adi
 */
public interface TransaksiService {
    
    public Purchase save(Purchase purchase);
    public Purchase findPurchaseById(String id);
    public Long countPurchaseByDate(Date startDate, Date endDate);
    public Page<Purchase> findAllPurchaseByDate(Date startDate, Date endDate, Pageable pageable);
    public List<Purchase> findAllPurchaseByPurchaseNumber(String purchaseNumber);
    
}