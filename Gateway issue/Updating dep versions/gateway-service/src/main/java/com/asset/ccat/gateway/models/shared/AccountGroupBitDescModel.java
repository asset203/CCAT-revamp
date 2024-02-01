package com.asset.ccat.gateway.models.shared;

import java.util.HashMap;

public class AccountGroupBitDescModel {

    private Integer bitPosition;
    private String bitName;
    private Boolean isEnabled;
    private HashMap<Integer, String> serviceClassBitDescriptions;

    public Integer getBitPosition() {
        return bitPosition;
    }

    public void setBitPosition(Integer bitPosition) {
        this.bitPosition = bitPosition;
    }

    public String getBitName() {
        return bitName;
    }

    public void setBitName(String bitName) {
        this.bitName = bitName;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public HashMap<Integer, String> getServiceClassBitDescriptions() {
        return serviceClassBitDescriptions;
    }

    public void setServiceClassBitDescriptions(HashMap<Integer, String> serviceClassBitDescriptions) {
        this.serviceClassBitDescriptions = serviceClassBitDescriptions;
    }
}
