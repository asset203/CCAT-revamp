package com.asset.ccat.gateway.mappers;

import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.AccountGroupBitModel;
import com.asset.ccat.gateway.models.customer_care.AccountGroupModel;
import com.asset.ccat.gateway.models.shared.AccountGroupBitDescModel;
import com.asset.ccat.gateway.models.shared.AccountGroupWithBitsModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class AccountGroupResponseMapper {

    public List<AccountGroupModel> map(HashMap<Integer, AccountGroupWithBitsModel> map,
                                       Integer serviceClassId) {
        CCATLogger.DEBUG_LOGGER.debug("AccountGroupResponseMapper() :Start Mapping");

        List<AccountGroupModel> list = new ArrayList<>();

        if (Objects.nonNull(map) && !map.isEmpty()) {
            map.forEach((accountGroupId, accountGroup) -> {
                AccountGroupModel mappedAccountGroup = new AccountGroupModel();
                //Setting Plan ID & Plan Name based on serviceClassId
                mappedAccountGroup.setId(accountGroupId);
                mappedAccountGroup.setName(accountGroup.getName());

                //Setting List Of Bits
                List<AccountGroupBitModel> bitsList = new ArrayList<>();
                List<AccountGroupBitDescModel> bitModelList = accountGroup.getBits();

                if (Objects.nonNull(bitModelList) && !bitModelList.isEmpty()) {
                    bitModelList.forEach((bitDetailsModel) -> {
                        AccountGroupBitModel bitModel = new AccountGroupBitModel();
                        bitModel.setIsEnabled(bitDetailsModel.getIsEnabled());
                        bitModel.setBitPosition(bitDetailsModel.getBitPosition());
                        HashMap<Integer, String> serviceClassDescForBits = bitDetailsModel.getServiceClassBitDescriptions();
                        if (serviceClassDescForBits != null
                                && !serviceClassDescForBits.isEmpty()
                                && serviceClassDescForBits.containsKey(serviceClassId)) {
                            bitModel.setBitName(serviceClassDescForBits.get(serviceClassId));
                        } else {
                            bitModel.setBitName(bitDetailsModel.getBitName());
                        }

                        bitsList.add(bitModel);
                    });
                }

                mappedAccountGroup.setBits(bitsList);
                list.add(mappedAccountGroup);
            });
        }
        CCATLogger.DEBUG_LOGGER.debug("AccountGroupResponseMapper() :End Mapping Successfully");

        return list;
    }
}
