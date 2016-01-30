/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dto;

import app.web.ecommerce.master.City;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author ilham-buru2@bsi
 */

public class MemberDTO implements Serializable {
    
    @NotNull @NotEmpty
    private String firstname;
    private String lastname;
    
    @Email
    @NotNull @NotEmpty
    private String email;
    
    @NotNull @NotEmpty
    private String address;
    
    @NotNull @NotEmpty
    private String province;
    
    @NotNull @ManyToOne
    private City city;
    private String zipCode;
    
    @NotNull @NotEmpty
    @NumberFormat @Length(min=11,max=15, message="panjang minimal 11 dan maksimal 15 karakter")
    private String mobile;
    private String phone;
    
    private Date registrationDate = new Date();
    
    @NotNull @NotEmpty
    @Length(min=6, message="panjang minimal 6 karakter")
    private String username;
    
    @NotNull @NotEmpty
    @Length(min=8, message="panjang minimal 8 karakter")
    private String password;
    
    @NotNull @NotEmpty
    @Length(min=8, message="panjang minimal 8 karakter")
    private String confirmPassword;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
}
