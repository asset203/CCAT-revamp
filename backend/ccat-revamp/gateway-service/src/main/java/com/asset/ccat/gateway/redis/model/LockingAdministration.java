package com.asset.ccat.gateway.redis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author wael.mohamed
 */
@RedisHash("LockingAdministration")
public class LockingAdministration implements Serializable {

    private static final long serialVersionUID = 1569761565518911826L;

    @Id
    private String msisdn;
    private String username;
    private Long date;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeToLive;

    public LockingAdministration() {
    }

    public LockingAdministration(String subscriber, String username, Long date) {
        this.msisdn = subscriber;
        this.username = username;
        this.date = date;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    @JsonIgnore
    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
        return "LockingAdministration{" +
                "msisdn='" + msisdn + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", timeToLive=" + timeToLive +
                '}';
    }
}
