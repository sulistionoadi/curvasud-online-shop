/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jimmy
 */
public interface PaymentDao extends JpaRepository<Payment, String>{
    
    @Query("select count(p) from Payment p where p.approved = :approved")
    public Long countPaymentByApproved(@Param("approved") boolean approved);
    
    public Page<Payment> findByApproved(boolean approved, Pageable pageable);
    
    public List<Payment> findByBooking(Booking booking);
    
}
