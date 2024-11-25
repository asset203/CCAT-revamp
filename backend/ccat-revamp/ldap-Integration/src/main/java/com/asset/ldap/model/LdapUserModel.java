package com.asset.ldap.model;

import com.asset.ldap.util.Util;

import java.util.Hashtable;

import java.util.Iterator;
import java.util.Set;

import javax.naming.directory.Attribute;

public class LdapUserModel extends LdapModel{
    private String nTAccount;
    private String nTPassowrd;
    private Hashtable attributes;
    public LdapUserModel() {
        attributes = new Hashtable();
    }

    public String getNTAccount() {
        if(nTAccount == null)
             this.setNTAccount(getAttribute(Util.getProperty("ldap.attribute.user.name")));
        return nTAccount;
    }

    private void setNTAccount(String nTAccount) {
        this.nTAccount = nTAccount;
    }
    /**
     * @modified-By: Ibrahim Sobhy
     * @modification-Date: 19-01-2009
     * @modification-Description: if att not found in LDAP return null 
     * */
    public String getAttribute(String attrName){
        if(ldapObjectModel == null)
        return null;
        else {
           Attribute attr = ldapObjectModel.getAttributes().get(attrName);
           if(attr != null)
             return reformatAttribute(attr.toString());
        }
        return null;
    }
    
    public void setAttribute(String attrName,String attrValue){
        if(ldapObjectModel != null)
        ldapObjectModel.getAttributes().put(attrName,attrValue);
        else
            attributes.put(attrName,attrValue);
    }
    public String getDisplayName(){
        return getAttribute(Util.getProperty("ldap.attribute.user.displayName"));
    }
    public Hashtable getAllAttributes(){
           return attributes;
    }
    /**
     * returns value after : 
     * @param att attribute in form name:value
     * @return value
     */
    private static String reformatAttribute (String att) {
        String data = null;
        int delIndex = att.indexOf(':');
        if (delIndex != -1)
            data = att.substring(delIndex + 2);
        data.trim();
        return data;
    }
    
    public String getSearchingFilterStr(){
        StringBuffer searchFilter = new StringBuffer("(&(objectClass="+Util.getProperty("ldap.objectclass.user")+")");
        Set searchingAttrs = this.getAllAttributes().keySet();
        Iterator myItr = searchingAttrs.iterator();
        while (myItr.hasNext()) {
            String attr = (String)myItr.next();
            searchFilter.append("(");
            searchFilter.append(attr);
            searchFilter.append("=");
            searchFilter.append(this.getAllAttributes().get(attr));
            searchFilter.append(")");
        }
        searchFilter.append(")");
        return searchFilter.toString();
    }

    public String getNTPassowrd() {
        return nTPassowrd;
    }

    public void setNTPassowrd(String nTPassowrd) {
        this.nTPassowrd = nTPassowrd;
    }
}
