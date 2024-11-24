package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.models.VoucherModel;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author assem.hassan
 */
@Component
public class GetVoucherDetailsMapper {

    @Autowired
    AIRUtils aIRUtils;

    public VoucherModel map(String voucherNumber, HashMap map) {

        String expiryDate = (String) map.get(AIRDefines.expiryDate);
        if (expiryDate != null && !expiryDate.equalsIgnoreCase("")) {
            expiryDate = aIRUtils.formatExpiryDate(aIRUtils.formatDateString(expiryDate));
        }

        String timestamp = (String) map.get(AIRDefines.timestamp);
        if (timestamp != null && !timestamp.equalsIgnoreCase("")) {
            timestamp = aIRUtils.formatExpiryTimeStamp(aIRUtils.formatDateString(timestamp));
        }
        String state = mapVoucherState((String) map.get(AIRDefines.state));
        String value = (String) map.get(AIRDefines.value);
        if (value != null && !value.equalsIgnoreCase("")) {
            value = aIRUtils.amountInLE(value).toString();
        }
        VoucherModel voucherModel = new VoucherModel();
        voucherModel.setResponseCode((String) map.get(AIRDefines.responseCode));
        voucherModel.setActivationCode((String) map.get(AIRDefines.activationCode));
        voucherModel.setAgent((String) map.get(AIRDefines.agent));
        voucherModel.setBatchId((String) map.get(AIRDefines.batchId));
        voucherModel.setCurrency((String) map.get(AIRDefines.currency));
        voucherModel.setExpiryDate(expiryDate);
        voucherModel.setOperatorId((String) map.get(AIRDefines.operatorId));
        voucherModel.setRechargeSource((String) map.get(AIRDefines.extensionText1));
        voucherModel.setState(state);
        voucherModel.setSubscriberId((String) map.get(AIRDefines.subscriberId));
        voucherModel.setTimeStamp(timestamp);
        voucherModel.setValue(value);
        voucherModel.setVoucherGroup((String) map.get(AIRDefines.voucherGroup));
        voucherModel.setVoucherSerialNumber(voucherNumber);

        return voucherModel;
    }

    private String mapVoucherState(String code) {
        String name = "State_" + code;
        if (code == null) {
            return name;
        }
        if (code.equals("0")) {
            return "Available";
        }
        if (code.equals("1")) {
            return "Used";
        }
        if (code.equals("2")) {
            return "Damaged";
        }
        if (code.equals("3")) {
            return "Stolen/Missing";
        }
        if (code.equals("4")) {
            return "Pending";
        }
        if (code.equals("5")) {
            return "Unavailable";
        }
        if (code.equals("6")) {
            return "Reserved";
        }
        return name;
    }
}
