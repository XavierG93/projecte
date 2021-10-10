/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.utils;

/**
 *
 * @author Gurrea
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
/*
Ho afegim als serveis amb el autowired
*/
@Component
public class PrivilegesChecker {

    public boolean hasPrivilege(String privilegeName, Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()).contains(privilegeName);
    }

    public boolean hasPrivileges(Collection<String> privilegeNames, Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()).containsAll(privilegeNames);
    }

}
