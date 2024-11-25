package com.asset.ccat.gateway.models.admin;


/**
 * @author mohamed.metwaly
 */
public class ExtractUserModel {
    private Integer userId;
    private String username;
    private String source;
    private Long creationDate;
    private Long modificationDate;
    private Long lastLogin;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "UserExtractModel{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", source='" + source + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
