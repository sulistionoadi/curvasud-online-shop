/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.domain.transaction;

import app.web.ecommerce.domain.BaseEntity;
import app.web.ecommerce.domain.master.Member;
import app.web.ecommerce.domain.master.Product;
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
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="trx_testimoni_product")
public class Testimoni extends BaseEntity{
    
    @NotNull @NotEmpty
    @Column(name="comment", nullable=false)
    private String comment;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="product_code", nullable=false, columnDefinition = "VARCHAR(8)")
    private Product product;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="member_code", nullable=false, columnDefinition = "VARCHAR(10)")
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
