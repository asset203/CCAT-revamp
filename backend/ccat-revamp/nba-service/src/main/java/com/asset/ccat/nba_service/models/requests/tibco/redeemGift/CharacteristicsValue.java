package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

public class CharacteristicsValue {
    public String characteristicName;
    public String value;

    public CharacteristicsValue() {
    }

    public CharacteristicsValue(String characteristicName, String value) {
        this.characteristicName = characteristicName;
        this.value = value;
    }

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
