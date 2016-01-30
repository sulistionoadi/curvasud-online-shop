/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.transaction;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="trx_testimoni_product")
public class Testimoni extends BaseEntity{
    
    @NotNull @NotEmpty
    @Column(name="comment", nullable=false)
    private String comment;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_product", nullable=false)
    private Product product;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_member", nullable=false)
    private Member member;
    
    @Column(name="testimoni_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
