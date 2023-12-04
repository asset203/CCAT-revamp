package com.asset.ccat.gateway.models.users;

/**
 *
 * @author nour.ihab
 */
public class GetUserModel {

    private Integer userId;
    private String ntAccount;
    private Integer profileId;
    private Integer sessionCounter;
    private Long rebateLimit;
    private Long debitLimit;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNtAccount() {
        return ntAccount;
    }

    public void setNtAccount(String ntAccount) {
        this.ntAccount = ntAccount;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getSessionCounter() {
        return sessionCounter;
    }

    public void setSessionCounter(Integer sessionCounter) {
        this.sessionCounter = sessionCounter;
    }

    public Long getRebateLimit() {
        return rebateLimit;
    }

    public void setRebateLimit(Long rebateLimit) {
        this.rebateLimit = rebateLimit;
    }

    public Long getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Long debitLimit) {
        this.debitLimit = debitLimit;
    }

}
