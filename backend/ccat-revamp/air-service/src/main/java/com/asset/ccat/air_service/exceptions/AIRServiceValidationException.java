/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.exceptions;

/**
 *
 * @author Mahmoud Shehab
 */
public class AIRServiceValidationException extends AIRServiceException {

    public AIRServiceValidationException(int errorCode, String args) {
        super(errorCode, args);
    }
}
