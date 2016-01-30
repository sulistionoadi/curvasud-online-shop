/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.constant.DesignationType;
import app.web.ecommerce.domain.RunningNumber;
import java.util.Date;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface RunningNumberDao extends PagingAndSortingRepository<RunningNumber, String> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from RunningNumber rn where year(rn.bussinessDate) = year(:date) and rn.designationType = :designation")
    public RunningNumber getNumberYearly(@Param("date") Date date, @Param("designation") DesignationType designationType);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from RunningNumber rn where year(rn.bussinessDate) = year(:date) and month(rn.bussinessDate) = month(:date) and rn.designationType = :designation")
    public RunningNumber getNumberMonthly(@Param("date") Date date, @Param("designation") DesignationType designationType);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from RunningNumber rn where rn.designationType = :designation")
    public RunningNumber getNumberProduct(@Param("designation") DesignationType designationType);
    
}
