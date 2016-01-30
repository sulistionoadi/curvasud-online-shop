/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service;

import app.web.ecommerce.domain.master.CategoryProduct;
import app.web.ecommerce.domain.master.Member;
import app.web.ecommerce.domain.master.Product;
import app.web.ecommerce.domain.security.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface MasterService {
    
    public CategoryProduct save(CategoryProduct categoryProduct);
    public void delete(CategoryProduct categoryProduct);
    public CategoryProduct findCategoryProductByKode(String kode);
    public Long countAllCategoryProuducts();
    public Page<CategoryProduct> findAllCategoryProducts(Pageable pageable);
    
    public Product save(Product product);
    public void delete(Product product);
    public Product findProductByKode(String kode);
    public Long countAllProducts();
    public Long countAllProductsByCategory(CategoryProduct cat);
    public Long countAllProductsByKeyword(String keyword);
    public Page<Product> findAllProducts(Pageable pageable);
    public Page<Product> findAllProductsByCategory(Pageable pageable, CategoryProduct cat);
    public Page<Product> findAllProductsByKeyword(Pageable pageable, String keyword);
    public List<Product> findAllProducts();
    
    public void register(Member member, User user);
    public void delete(Member member);
    public void save(Member member);
    public Member findMemberByKode(String kode);
    public Long countAllMembers();
    public Page<Member> findAllMembers(Pageable pageable);

}
