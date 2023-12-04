package com.asset.ccat.gateway.models.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author nour.ihab
 */
public class LockingAdministrationModel {

    private String subscriber;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Long date;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}
