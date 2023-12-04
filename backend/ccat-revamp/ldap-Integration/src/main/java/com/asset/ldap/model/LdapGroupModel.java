package com.asset.ldap.model;

import com.asset.ldap.util.Util;


public class LdapGroupModel extends LdapModel{
    private String groupName;
    public LdapGroupModel() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getAttribute(String attrName){
        if(ldapObjectModel == null)
        return null;
        else
        return reformatAttribute(ldapObjectModel.getAttributes().get(attrName).toString());
    }
    public String getDisplayName(){
        if(groupName != null)
            return groupName;
        else
            return getAttribute(Util.getProperty("ldap.attribute.group.name"));
    }
    private static String reformatAttribute (String att) {
        String data = null;
        int delIndex = att.indexOf(':');
        if (delIndex != -1)
            data = att.substring(delIndex + 2);
        data.trim();
        return data;
    }
}
