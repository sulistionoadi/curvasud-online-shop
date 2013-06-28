/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Supplier;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author adi
 */
public interface MasterService {
    
    public CategoryProduct save(CategoryProduct categoryProduct);
    public void delete(CategoryProduct categoryProduct);
    public CategoryProduct findCategoryProductById(String id);
    public CategoryProduct findCategoryProductByKode(String kode);
    public Long countAllCategoryProuducts();
    public Page<CategoryProduct> findAllCategoryProducts(Pageable pageable);
    
    public Product save(Product product);
    public void delete(Product product);
    public Product findProductById(String id);
    public Product findProductByKode(String kode);
    public Long countAllProducts();
    public Page<Product> findAllProducts(Pageable pageable);
    
    public Supplier save(Supplier supplier);
    public void delete(Supplier supplier);
    public Supplier findSupplierById(String id);
    public Supplier findSupplierByKode(String kode);
    public Long countAllSupplier();
    public Page<Supplier> findAllSupplier(Pageable pageable);
    
    public void register(Member member, User user);
    public void delete(Member member);
    public Member findMemberById(String id);
    public Long countAllMembers();
    public Page<Member> findAllMembers(Pageable pageable);
    
}
