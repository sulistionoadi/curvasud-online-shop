/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dto;

import app.web.ecommerce.master.Member;
import app.web.ecommerce.master.Product;
import java.math.BigDecimal;

/**
 *
 * @author adi
 */


public class ShoppingCartDTO {
    
    private Member member;
    private Product product;
    private Integer qty;
    private BigDecimal total;
    private BigDecimal ongkir;

    public BigDecimal getOngkir() {
        return ongkir;
    }

    public void setOngkir(BigDecimal ongkir) {
        this.ongkir = ongkir;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
