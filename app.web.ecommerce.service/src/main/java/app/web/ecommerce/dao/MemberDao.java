/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.master.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface MemberDao extends PagingAndSortingRepository<Member, String> {
    
    Member findByMemberCode(String kode);
    
}
