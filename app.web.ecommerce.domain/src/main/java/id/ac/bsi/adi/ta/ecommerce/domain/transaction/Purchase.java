/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.transaction;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Supplier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */
@Entity
@Table(name="trx_purchase")
public class Purchase extends BaseEntity {
   
   @NotNull @NotEmpty
   @Column(nullable=false, name="purchase_number", length=20)
   private String purchaseNumber;
   
   @Column(nullable=false, name="purchase_date")
   @Temporal(TemporalType.TIMESTAMP)
   private Date purchaseDate = new Date();
   
   @NotNull
   @ManyToOne
   @JoinColumn(name="id_supplier", nullable=false)
   private Supplier supplier;
   
   @OneToMany(mappedBy="purchase")
   @Cascade(CascadeType.ALL)
   private List<PurchaseDetail> purchaseDetails = new ArrayList<PurchaseDetail>();
   
   public BigDecimal getSummaryPrice(){
       BigDecimal summary = BigDecimal.ZERO;
       for (PurchaseDetail pd : purchaseDetails) {
           summary = summary.add(pd.getTotalPrice());
       }
       return summary;
   }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetail> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }
    
}
