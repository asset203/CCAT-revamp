package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class ProfilesServiceClassesExtractor implements ResultSetExtractor<HashMap<Integer, List<AdmServiceClassResponse>>> {

    @Override
    public HashMap<Integer, List<AdmServiceClassResponse>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, List<AdmServiceClassResponse>> profilesSCMap = new HashMap<>();
        Integer profileId;
        AdmServiceClassResponse serviceClassModel;

        while (rs.next()) {
            profileId = rs.getInt(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID);
            if (profilesSCMap.get(profileId) == null) {
                profilesSCMap.put(profileId, new ArrayList<>());
            }
            serviceClassModel = new AdmServiceClassResponse();
            serviceClassModel.setCode(rs.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
            serviceClassModel.setName(rs.getString(DatabaseStructs.ADM_SERVICE_CLASSES.NAME));
            profilesSCMap.get(profileId).add(serviceClassModel);

        }
        return profilesSCMap;
    }

}
