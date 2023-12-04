package com.asset.ccat.ods_service.database.mapper.custom_mappers;

import com.asset.ccat.ods_service.cache.CachedLookups;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityHeaderMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceTransferObjectMapper implements CustomMapper {

    @Autowired
    CachedLookups cachedLookups;

//    @Override
//    public void handleCustomMapping(SubscriberActivityModel accountHistoryModel, String msisdn, Object[] columns, ODSActivityHeaderMapping headerMappingObject) {
//        String[] preCond = headerMappingObject.getPreConditions().split("\\$"); //CUSTOM$
//        String[] args = preCond[1].split(",");//srcIdx=2,desIdx=3,debitAmountIdx=6,creditAmountIdx=7
//        int srcIdx = Integer.parseInt(args[0].split("=")[1]);
//        int desIdx = Integer.parseInt(args[1].split("=")[1]);
//        int debAmountIdx = Integer.parseInt(args[2].split("=")[1]);
//        int creAmountIdx = Integer.parseInt(args[3].split("=")[1]);
//
//        int statusIdx = headerMappingObject.getColumnIdx();
//
//        String srcMsisdn = (String) columns[srcIdx];
//        String dstMsisdn = (String) columns[desIdx];
//        String status = (String) columns[statusIdx];
//
//        if (status == null || "".equalsIgnoreCase(status)) {
//            if (srcMsisdn.equals(msisdn)) {
//                accountHistoryModel.setSubType("DEB");
//            } else if (dstMsisdn != null && dstMsisdn.equals(msisdn)) {
//                accountHistoryModel.setSubType("CRE");
//            }
//        } else {
//            String val = cachedLookups.getBtStatus() != null ?
//                    cachedLookups.getBtStatus().get(status) : status;
//            accountHistoryModel.setSubType(val);
//        }
//
//        if (accountHistoryModel.getSubType() != null && accountHistoryModel.getSubType().equals("DEB")) {
//            accountHistoryModel.setAmount(columns[debAmountIdx] == null ?
//                    0 : Double.parseDouble((String) columns[debAmountIdx]));
//        } else if (accountHistoryModel.getSubType() != null && accountHistoryModel.getSubType().equals("CRE")) {
//            accountHistoryModel.setAmount(columns[creAmountIdx] == null ?
//                    0 : Double.parseDouble((String) columns[creAmountIdx]));
//        }
//
//    }

    @Override
    public void handleCustomMapping(SubscriberActivityModel accountHistoryModel, String msisdn, Object[] columns, ODSActivityHeaderMapping headerMappingObject) {
        String[] preCond = headerMappingObject.getPreConditions().split("\\?"); //CUSTOM?{2}=$MSISDN$,{3}=$MSISDN$
        String[] args = preCond[1].split(",");
        int srcIdx = Integer.parseInt(args[0].split("=")[1]);
        int desIdx = Integer.parseInt(args[1].split("=")[1]);


        String[] preConditionValues = headerMappingObject.getPreConditionsValue().split(","); //1=$DEB$,2=$CRE$
        String debitValue = preConditionValues[0].split("=")[1].replace("$","");
        String creditValue = preConditionValues[1].split("=")[1].replace("$","");

        int debAmountIdx = Integer.parseInt(preConditionValues[2].split("=")[1]);
        int creAmountIdx = Integer.parseInt(preConditionValues[3].split("=")[1]);

        int statusIdx = headerMappingObject.getColumnIdx();

        String srcMsisdn = (String) columns[srcIdx];
        String dstMsisdn = (String) columns[desIdx];
        String status = (String) columns[statusIdx];

        if (status == null || "".equalsIgnoreCase(status)) {
            if (srcMsisdn.equals(msisdn)) {
                accountHistoryModel.setSubType(debitValue);
            } else if (dstMsisdn != null && dstMsisdn.equals(msisdn)) {
                accountHistoryModel.setSubType(creditValue);
            }
        } else {
            String val = cachedLookups.getBtStatus() != null ?
                    cachedLookups.getBtStatus().get(status) : status;
            accountHistoryModel.setSubType(val);
        }

        if (accountHistoryModel.getSubType() != null && accountHistoryModel.getSubType().equals(debitValue)) {
            accountHistoryModel.setAmount(columns[debAmountIdx] == null ?
                    0 : Double.parseDouble((String) columns[debAmountIdx]));
        } else if (accountHistoryModel.getSubType() != null && accountHistoryModel.getSubType().equals(creditValue)) {
            accountHistoryModel.setAmount(columns[creAmountIdx] == null ?
                    0 : Double.parseDouble((String) columns[creAmountIdx]));
        }

    }

}
