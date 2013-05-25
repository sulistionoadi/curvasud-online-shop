/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.master;

import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="mst_member")
public class Member extends BaseEntity {
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String firstname;
    private String lastname;
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String email;
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String address;
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String province;
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String city;
    
    @Column(name="zip_code")
    private String zipCode;
    
    @NotNull @NotEmpty
    @Column(nullable=false)
    private String phone;
    
    private String mobile;
    
    @Column(nullable=false, name="registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate = new Date();
    
    @NotNull @NotEmpty
    @Transient
    private String username;
    
    @NotNull @NotEmpty
    @Transient
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
