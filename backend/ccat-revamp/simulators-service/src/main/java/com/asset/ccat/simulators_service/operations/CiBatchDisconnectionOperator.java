
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;
import java.io.IOException;

/**
 *
 * @author mahmoud.shehab
 */
public class CiBatchDisconnectionOperator implements Operation {

    @Override
    public String operate(UCIPModel model) {
        String filePath = model.getPath() + "BatchDisconnection";

        String returnBody = "";
        try {
            returnBody = FileUtils.readFileAsString(filePath, model);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return returnBody;

    }

}
