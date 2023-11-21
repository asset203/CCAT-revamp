package com.asset.ldap.util;

import com.asset.ldap.model.LdapModel;

import java.util.Comparator;

public class ModelComparator implements Comparator{
    public ModelComparator() {
    }
    public int compare(Object obj1, Object obj2) {
        LdapModel model1 = (LdapModel) obj1;
        LdapModel model2 = (LdapModel) obj2;
        String m1Name = model1.getDisplayName();
        String m2Name = model2.getDisplayName();
        return m1Name.toLowerCase().compareTo(m2Name.toLowerCase());
    }
}
