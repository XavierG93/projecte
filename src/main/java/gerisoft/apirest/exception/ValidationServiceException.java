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
public class ValidationServiceException extends Exception{
    
    public ValidationServiceException(String message){
        super("Validation error in service: " + message);
    }
    
}
