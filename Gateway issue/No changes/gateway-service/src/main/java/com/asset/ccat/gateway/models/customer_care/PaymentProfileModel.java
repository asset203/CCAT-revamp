package com.asset.ccat.gateway.models.customer_care;

/**
 *
 * @author nour.ihab
 */
public class PaymentProfileModel {

    private String profileId;
    private String profileName;
    private Float amountFrom;
    private Float amountTo;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Float getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Float amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Float getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Float amountTo) {
        this.amountTo = amountTo;
    }

}
