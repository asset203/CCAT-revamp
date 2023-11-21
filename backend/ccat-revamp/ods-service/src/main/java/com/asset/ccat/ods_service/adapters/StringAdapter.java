package com.asset.ccat.ods_service.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Mahmoud Shehab
 */
public class StringAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String val) {
        return null;
    }

    @Override
    public String unmarshal(String val) {
        if (val != null && !"N/A".equals(val)) {
            return val;
        }
        return null;
    }

}
