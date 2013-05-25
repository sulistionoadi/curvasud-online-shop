/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.master;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="mst_product")
public class Product extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name="id_category", nullable=false)
    private CategoryProduct category;
    
    @NotNull @NotEmpty
    @Column(nullable=false, name="product_code", unique=true)
    private String productCode;
    
    @NotNull @NotEmpty
    @Column(nullable=false, name="product_name")
    private String productName;
    
    @Lob
    @Column(nullable=false, name="product_info")
    private String productInfo;
    
    @NotNull
    @Column(nullable=false)
    private BigDecimal price;
    
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name="mst_picture_product", 
            joinColumns=@JoinColumn(name="id_product"))
    private List<String> pictures = new ArrayList<String>();

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
    
}
