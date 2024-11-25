package com.asset.ldap.model;

import javax.naming.directory.SearchResult;

public abstract class LdapModel {
    protected String displayName;
    protected SearchResult ldapObjectModel;
    public LdapModel() {
    }

    abstract public String getDisplayName();

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }
    
    public SearchResult getLdapObjectModel() {
        return ldapObjectModel;
    }

    public void setLdapObjectModel(SearchResult ldapObjectModel) {
        this.ldapObjectModel = ldapObjectModel;
    }
}
