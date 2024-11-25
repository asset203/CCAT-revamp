package com.asset.ccat.ods_service.database.mapper.custom_mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomMapperFactory {

    @Autowired
    ApplicationContext applicationContext;

    public CustomMapper getCustomMapper(String type){
        if("H_BALANCE_TRANSFERS".equals(type)){
            CustomMapper mapper = applicationContext.getBean(BalanceTransferObjectMapper.class);
            return mapper;
        }
        return null;
    }
}
