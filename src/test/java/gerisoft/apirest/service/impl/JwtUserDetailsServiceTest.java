package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JwtUserDetailsServiceTest extends SimpleBaseTestCase {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void loadUserByUsername_OK() {
        //GIVEN
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CREATE_USER"));
        UserDetails expectedUserDetail = new org.springframework.security.core.userdetails.User(
                "admin", "1234", authorities);
        Priviliege p = new Priviliege();
        p.setName("CREATE_USER");
        Role role = new Role();
        role.setName("ADMIN");
        List privilieges = new ArrayList();
        privilieges.add(p);
        role.setPriviliege(privilieges);
        User user = new User();
        user.setRole(role);
        user.setUsername("admin");
        user.setPassword("1234");
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        //when
        UserDetails result = jwtUserDetailsService.loadUserByUsername("admin");
        //then
        Assertions.assertEquals(expectedUserDetail, result);

    }

    @Test
    void loadUserByUserName_usernameNotFoundException() {
        //GIVEN
        String username = "FOO";
        Mockito.when(userRepository.findByUsername("FOO")).thenReturn(Optional.empty());
        //when then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("admin"));
    }
}
