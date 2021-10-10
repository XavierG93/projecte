/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.exception;

/**
 *
 * @author Gurrea
 */
public class PrivilegesException extends Exception {

    public PrivilegesException(String requiredPrivilege) {
        super("You dont have the required privilege: " + requiredPrivilege);
    }

}