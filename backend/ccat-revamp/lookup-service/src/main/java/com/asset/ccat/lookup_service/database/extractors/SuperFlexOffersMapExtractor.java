package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.SuperFlexLookupModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SuperFlexOffersMapExtractor implements ResultSetExtractor<HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>>> {
    @Override
    public HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> map = new HashMap<>();
        SuperFlexLookupModel model;
        int typeId = -1;
        int offerId = -1;
        while (rs.next()) {
            model = new SuperFlexLookupModel();
            model.setId(rs.getInt(DatabaseStructs.LK_FLEX_OFFERS.ID));
            model.setTypeID(rs.getInt(DatabaseStructs.LK_FLEX_OFFERS.TYPE_ID));
            model.setName(rs.getString(DatabaseStructs.LK_FLEX_OFFERS.NAME));
            model.setDescription(rs.getString(DatabaseStructs.LK_FLEX_OFFERS.DESCRIPTION));
            model.setPackageID(rs.getString(DatabaseStructs.LK_FLEX_OFFERS.PACKAGE_ID));
            model.setOfferID(rs.getInt(DatabaseStructs.LK_FLEX_OFFERS.OFFER_ID));
            typeId = model.getTypeID();
            offerId = model.getOfferID();

            if (!map.containsKey(typeId)) {
                map.put(typeId, new HashMap<>());
            }
            map.get(typeId).put(offerId, model);
        }

        return map;
    }
}
