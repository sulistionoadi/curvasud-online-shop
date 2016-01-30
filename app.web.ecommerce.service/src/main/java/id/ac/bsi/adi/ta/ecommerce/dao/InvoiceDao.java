/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author jimmy
 */
public interface InvoiceDao extends PagingAndSortingRepository<Invoice, String>, JpaRepository<Invoice, String> {
    
    public Invoice findByInvoiceNumber(String number);
    
}
