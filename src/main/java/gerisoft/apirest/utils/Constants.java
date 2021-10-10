package gerisoft.apirest.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Gurrea
 */
public class Constants {
    
    public static final String ADMIN_USERNAME = "admin";
    
    // roles constants
    public static final String ADMIN_ROLE_NAME = "ADMIN_ROLE";
    public static final String TRABAJADOR_ROLE_NAME = "TRABAJADOR_ROLE";
    public static final String AUXILIAR_ROLE_NAME = "AUXILIAR_ROLE";
    
    // privileges
    public static final String CREATE_TRABAJADOR_PRIVILEGE = "CREATE_TRABAJADOR";
    public static final String READ_TRABAJADOR_PRIVILEGE = "READ_TRABAJADOR";
    public static final String UPDATE_TRABAJADOR_PRIVILEGE = "UPDATE_TRABAJADOR";
    public static final String DELETE_TRABAJADOR_PRIVILEGE = "DELETE_TRABAJADOR";
    
    public static final String CREATE_AUXILIAR_PRIVILEGE = "CREATE_AUXILIAR";
    public static final String READ_AUXILIAR_PRIVILEGE = "READ_AUXILIAR";
    public static final String UPDATE_AUXILIAR_PRIVILEGE = "UPDATE_AUXILIAR";
    public static final String DELETE_AUXILIAR_PRIVILEGE = "DELETE_AUXILIAR";
    
    public static final String CREATE_RESIDENTE_PRIVILEGE = "CREATE_RESIDENTE";
    public static final String READ_RESIDENTE_PRIVILEGE = "READ_RESIDENTE";
    public static final String UPDATE_RESIDENTE_PRIVILEGE = "UPDATE_RESIDENTE";
    public static final String DELETE_RESIDENTE_PRIVILEGE = "DELETE_RESIDENTE";
    
    public static final String CREATE_ACTIVIDAD_PRIVILEGE = "CREATE_ACTIVIDAD";
    public static final String READ_ACTIVIDAD_PRIVILEGE = "READ_ACTIVIDAD";
    public static final String UPDATE_ACTIVIDAD_PRIVILEGE = "UPDATE_ACTIVIDAD";
    public static final String DELETE_ACTIVIDAD_PRIVILEGE = "DELETE_ACTIVIDAD";
    
    /*public static final String CREATE_ADMIN_PRIVILEGE = "CREATE_ACTIVIDAD";
    public static final String READ_ADMIN_PRIVILEGE = "READ_ACTIVIDAD";
    public static final String UPDATE_ADMIN_PRIVILEGE = "UPDATE_ACTIVIDAD";
    public static final String DELETE_ADMIN_PRIVILEGE = "DELETE_ACTIVIDAD";*/
    
    public static final String CREATE_ATENCION_PRIVILEGE = "CREATE_ATENCION";
    public static final String READ_ATENCION_PRIVILEGE = "READ_ATENCION";
    public static final String UPDATE_ATENCION_PRIVILEGE = "UPDATE_ATENCION";
    public static final String DELETE_ATENCION_PRIVILEGE = "DELETE_ATENCION";
    
    // Role List
    public static final List<String> ROLES_LIST = new ArrayList<String>() {
        {
            add(ADMIN_ROLE_NAME);
            add(TRABAJADOR_ROLE_NAME);
            add(AUXILIAR_ROLE_NAME);
        }
    };
    
    public static final List<String> PRIVILEGES_LIST = new ArrayList<String>() {
        {
            add(CREATE_TRABAJADOR_PRIVILEGE);
            add(READ_TRABAJADOR_PRIVILEGE);
            add(UPDATE_TRABAJADOR_PRIVILEGE);
            add(DELETE_TRABAJADOR_PRIVILEGE);
            
            add(CREATE_AUXILIAR_PRIVILEGE);
            add(READ_AUXILIAR_PRIVILEGE);
            add(UPDATE_AUXILIAR_PRIVILEGE);
            add(DELETE_AUXILIAR_PRIVILEGE);
            
            add(CREATE_RESIDENTE_PRIVILEGE);
            add(READ_RESIDENTE_PRIVILEGE);
            add(UPDATE_RESIDENTE_PRIVILEGE);
            add(DELETE_RESIDENTE_PRIVILEGE);
            
            add(CREATE_ACTIVIDAD_PRIVILEGE);
            add(READ_ACTIVIDAD_PRIVILEGE);
            add(UPDATE_ACTIVIDAD_PRIVILEGE);
            add(DELETE_ACTIVIDAD_PRIVILEGE);
            
            add(CREATE_ATENCION_PRIVILEGE);
            add(READ_ATENCION_PRIVILEGE);
            add(UPDATE_ATENCION_PRIVILEGE);
            add(DELETE_ATENCION_PRIVILEGE);
        }
    };
    
    private static final List<String> PRIVILEGES_BY_ADMIN = PRIVILEGES_LIST;
    
    private static final List<String> PRIVILEGES_BY_TRABAJADOR = new ArrayList<String>() {
        {
            add(CREATE_RESIDENTE_PRIVILEGE);
            add(READ_RESIDENTE_PRIVILEGE);
            add(UPDATE_RESIDENTE_PRIVILEGE);
            add(DELETE_RESIDENTE_PRIVILEGE);
            
            add(CREATE_ACTIVIDAD_PRIVILEGE);
            add(READ_ACTIVIDAD_PRIVILEGE);
            add(UPDATE_ACTIVIDAD_PRIVILEGE);
            add(DELETE_ACTIVIDAD_PRIVILEGE);
            
            add(CREATE_ATENCION_PRIVILEGE);
            add(READ_ATENCION_PRIVILEGE);
            add(UPDATE_ATENCION_PRIVILEGE);
            add(DELETE_ATENCION_PRIVILEGE);
        }
    };
    private static final List<String> PRIVILEGES_BY_AUXILIAR = new ArrayList<String>() {
        {
            add(READ_RESIDENTE_PRIVILEGE);
                        
            add(READ_ATENCION_PRIVILEGE);
            add(UPDATE_ATENCION_PRIVILEGE);  
        }
    };
    
    // Privileges List indexed by Role
    public static final Map<String, List<String>> PRIVILEGES_BY_ROLE;
    static {
        Map<String, List<String>> privilegesByRole = new HashMap<>();
        privilegesByRole.put(Constants.ADMIN_ROLE_NAME, Constants.PRIVILEGES_BY_ADMIN);
        privilegesByRole.put(Constants.TRABAJADOR_ROLE_NAME, Constants.PRIVILEGES_BY_TRABAJADOR);
        privilegesByRole.put(Constants.AUXILIAR_ROLE_NAME, Constants.PRIVILEGES_BY_AUXILIAR);

        PRIVILEGES_BY_ROLE = Collections.unmodifiableMap(privilegesByRole);
    };
}
