/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.domain.security;

import id.ac.bsi.adi.ta.ecommerce.constant.StatusUser;
import id.ac.bsi.adi.ta.ecommerce.domain.BaseEntity;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author adi
 */

@Entity
@Table(name="sec_user")
public class User extends BaseEntity {
    
    @NotNull @NotEmpty
    @Column(nullable=false, unique=true, length=20)
    private String username;
    
    @NotNull @NotEmpty
    @Length(min=8)
    @Column(nullable=false, length=30)
    private String password;
   
    @Transient
    private String confirm;
    
    @Column(nullable=false)
    private Boolean active = Boolean.FALSE;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=6)
    private StatusUser status = StatusUser.NEW;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;
    
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_member")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public StatusUser getStatus() {
        return status;
    }

    public void setStatus(StatusUser status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
}
