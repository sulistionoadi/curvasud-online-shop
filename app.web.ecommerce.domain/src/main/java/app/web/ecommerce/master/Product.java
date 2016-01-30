/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.master;

import app.web.ecommerce.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author ilham-buru2@bsi
 */

@Entity
@Table(name="mst_product")
public class Product {
    
    @Id @NotNull @NotEmpty
    @Column(name="product_code", length=8)
    private String productCode;
    
    @ManyToOne @NotNull
    @JoinColumn(name="id_category", nullable=false)
    private CategoryProduct category;
    
    @NotNull @NotEmpty
    @Column(nullable=false, name="product_name", length=25)
    private String productName;
    
    @Lob
    @Column(nullable=false, name="product_info", length=999)
    private String productInfo;
    
    @NotNull
    @Column(nullable=false)
    private BigDecimal price;
    
    @NotNull
    @Min((long) 0.01)
    @Column(nullable=false)
    private BigDecimal weight = new BigDecimal("0.00");
    
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name="mst_picture_product", 
            joinColumns=@JoinColumn(name="id_product"))
    @Column(name="pictures", length=50)
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
}
