package com.asset.ldap.common;

/**
 * ldapfastbind.java
 *
 * Sample JNDI application to use Active Directory LDAP_SERVER_FAST_BIND
 * connection control
 *
 */
import javax.naming.ldap.*;

public class FastBindConnectionControl implements Control {

    public byte[] getEncodedValue() {
        return null;
    }

    public String getID() {
        return "1.2.840.113556.1.4.1781";
    }

    public boolean isCritical() {
        return true;
    }

}
