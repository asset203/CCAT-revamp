/*
 * To change this license title, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.balance_dispute_service.dto.users;

/**
 *
 * @author wael.mohamed
 */
public class MarqueeModel {

    private int id;
    private String title;
    private String description;

    public MarqueeModel() {
    }

    public MarqueeModel(String title, String description) {

        this.title = title;
        this.description = description;
    }

    public MarqueeModel(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
