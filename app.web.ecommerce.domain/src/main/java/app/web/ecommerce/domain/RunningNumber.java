/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.domain;

import app.web.ecommerce.constant.DesignationType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="mst_running_number",
        uniqueConstraints=@UniqueConstraint(columnNames={"designation_type", "bussiness_date"}))
public class RunningNumber extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(name="designation_type", nullable=false, length=10)
    private DesignationType designationType;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="bussiness_date", nullable=false)
    private Date bussinessDate = new Date();
    
    @Column(name="last_number", nullable=false)
    private Long lastNumber = 0L;

    public DesignationType getDesignationType() {
        return designationType;
    }

    public void setDesignationType(DesignationType designationType) {
        this.designationType = designationType;
    }

    public Date getBussinessDate() {
        return bussinessDate;
    }

    public void setBussinessDate(Date bussinessDate) {
        this.bussinessDate = bussinessDate;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Long lastNumber) {
        this.lastNumber = lastNumber;
    }
    
}
