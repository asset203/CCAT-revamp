/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.exceptions;

/**
 *
 * @author Mahmoud Shehab
 */
public class GatewayValidationException extends GatewayException {

    public GatewayValidationException(int errorCode, String... args) {
        super(errorCode, null, args);
    }

}
