/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shehab
 */
public class LoginRequest extends BaseRequest implements Serializable {

    private String username;
    private String password;
    private String machineName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMachineName() {
        return (super.getFootprintModel() != null) ? super.getFootprintModel().getMachineName() : this.machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
