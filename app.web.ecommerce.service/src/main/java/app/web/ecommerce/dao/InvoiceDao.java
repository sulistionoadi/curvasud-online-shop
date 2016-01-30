/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.transaction.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author jimmy
 */
public interface InvoiceDao extends PagingAndSortingRepository<Invoice, String>, JpaRepository<Invoice, String> {
    
    public Invoice findByInvoiceNumber(String number);
    
}
