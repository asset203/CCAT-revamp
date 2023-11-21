package com.asset.ldap.util;

import java.util.ResourceBundle;

public class Util {
    public static ResourceBundle bundle = 
        java.util.ResourceBundle.getBundle("ldap");
    public Util() {
    }
    
    public static String getProperty(String propKey){
        return bundle.getString(propKey);
    }
}
