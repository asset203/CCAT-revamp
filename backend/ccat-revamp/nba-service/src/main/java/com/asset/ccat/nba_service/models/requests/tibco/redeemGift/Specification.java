package com.asset.ccat.nba_service.models.requests.tibco.redeemGift;

import java.util.ArrayList;

public class Specification {
    public ArrayList<CharacteristicsValue> characteristicsValue;

    public Specification() {
    }

    public Specification(ArrayList<CharacteristicsValue> characteristicsValue) {
        this.characteristicsValue = characteristicsValue;
    }

    public ArrayList<CharacteristicsValue> getCharacteristicsValue() {
        return characteristicsValue;
    }

    public void setCharacteristicsValue(ArrayList<CharacteristicsValue> characteristicsValue) {
        this.characteristicsValue = characteristicsValue;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "characteristicsValue=" + characteristicsValue +
                '}';
    }
}
