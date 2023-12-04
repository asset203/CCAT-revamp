/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;
import java.io.IOException;

/**
 *
 * @author mahmoud.shehab
 */
public class GeneralOperation implements Operation {

    @Override
    public String operate(UCIPModel model) {

        String filePath = model.getPath() + model.getMethod();

        String returnBody = "";
        try {
            returnBody = FileUtils.readFileAsString(filePath, model);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return returnBody;
    }

}
