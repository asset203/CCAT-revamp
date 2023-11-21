package com.asset.ccat.ci_service.models.shared;

/**
 *
 * @author marwa.elshawarby
 */
public class LkMainProductType {

    private String type;
    private String displayName;

    public LkMainProductType(String type) {
        this.type = type;
    }

    public LkMainProductType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
