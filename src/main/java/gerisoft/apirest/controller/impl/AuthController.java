/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

/**
 *
 * @author Gurrea
 */
import gerisoft.apirest.config.JwtToken;
import gerisoft.apirest.dto.jwt.JwtRequest;
import gerisoft.apirest.dto.jwt.JwtResponse;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.service.UserService;
import gerisoft.apirest.service.impl.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService usersService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword(), userDetails.getAuthorities());

        // We need to by pass security in the userService to retrieve the user, because we are not still authenticated...
        boolean saltarSeguridad=true;
        User user = usersService.findByUsername(authenticationRequest.getUsername(),saltarSeguridad).get();
        final String token = jwtToken.generateToken(userDetails, user);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Transactional
    private void authenticate(String username, String password, Collection<? extends GrantedAuthority> authorities) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, authorities));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
