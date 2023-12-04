package com.asset.ccat.nba_service.models.requests.tibco.sendSMS;

import java.util.ArrayList;

public class Specification {
    public ArrayList<CharacteristicsValue> characteristicsValue;

    public ArrayList<CharacteristicsValue> getCharacteristicsValue() {
        return characteristicsValue;
    }

    public void setCharacteristicsValue(ArrayList<CharacteristicsValue> characteristicsValue) {
        this.characteristicsValue = characteristicsValue;
    }
}
