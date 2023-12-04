package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.SmsTemplateParamModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Component
public class SmsTemplateParamExtractor implements ResultSetExtractor<HashMap<Integer, List<SmsTemplateParamModel>>> {

    @Override
    public HashMap<Integer, List<SmsTemplateParamModel>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateParamMap = new HashMap<>();
        List<SmsTemplateParamModel> list;
        int actionId;
        while (resultSet.next()) {
            actionId = resultSet.getInt(DatabaseStructs.LK_SMS_TEMPLATE_PARAM.SMS_ACTION_ID);
            SmsTemplateParamModel smsTemplateParam = new SmsTemplateParamModel();
            smsTemplateParam.setParameterId(resultSet.getInt(DatabaseStructs.LK_SMS_TEMPLATE_PARAM.PARAMETER_ID));
            smsTemplateParam.setParameterName(resultSet.getString(DatabaseStructs.LK_SMS_TEMPLATE_PARAM.PARAMETER_NAME));
            if (Objects.isNull(smsTemplateParamMap.get(actionId))) {
                list = new ArrayList<>();
                smsTemplateParam.setSequenceId(1);
                list.add(smsTemplateParam);
                smsTemplateParamMap.put(actionId, list);
            } else {
                list = smsTemplateParamMap.get(actionId);
                smsTemplateParam.setSequenceId(list.size() + 1);
                list.add(smsTemplateParam);
            }
        }
        return smsTemplateParamMap;
    }
}
