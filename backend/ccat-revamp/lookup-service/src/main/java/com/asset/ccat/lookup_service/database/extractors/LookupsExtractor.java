/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.models.LookupModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class LookupsExtractor implements ResultSetExtractor<ArrayList<LookupModel>> {

    @Override
    public ArrayList<LookupModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ArrayList result = new ArrayList();
        while (resultSet.next()) {
            String key = resultSet.getString("ID");
            String value = resultSet.getString("NAME");
            LookupModel lookupModel = new LookupModel(key, value);
            result.add(lookupModel);
        }

        return result;
    }

}
