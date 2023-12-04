package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.mappers.MainProductMapper;
import com.asset.ccat.ci_service.models.responses.GetMainProductResponse;
import com.asset.ccat.ci_service.models.shared.MainProductModel;
import com.asset.ccat.ci_service.proxy.MainProductProxy;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marwa.elshawarby
 */
@Service
public class MainProductService {
    
    @Autowired
    private LookupService lookupService;
    
    @Autowired
    private MainProductProxy mainProductProxy;
    
    @Autowired
    private MainProductMapper mainProductMapper;
    
    public List<GetMainProductResponse> getMainProducts(String relatedPartyMsisdn) throws CIServiceException {
        
        CCATLogger.DEBUG_LOGGER.info("Start get main products service for [" + relatedPartyMsisdn + "]");

        //getting  main product types
        CCATLogger.DEBUG_LOGGER.debug("Retrieving main product types from lookupservice");
        HashMap<String, String> productTypes = lookupService.getMainProductTypes();
        CCATLogger.DEBUG_LOGGER.debug("Finished retrieving main product types from lookupservice");

        // retrieveing main products
        CCATLogger.DEBUG_LOGGER.debug("Retrieving main products from external system");
        List<MainProductModel> mainProducts = mainProductProxy.getMainProducts(relatedPartyMsisdn);
        CCATLogger.DEBUG_LOGGER.debug("Retrieving main products from external system");

        //filtering main products
        CCATLogger.DEBUG_LOGGER.debug("Filtering retrieved products based on type");
         mainProducts = mainProducts.stream()
                .filter((product)
                        -> productTypes.containsKey(product.getType())
                ).collect(Collectors.toList());

        List<GetMainProductResponse> filteredProducts = mainProducts.stream()
                .map((product) -> {
                    return mainProductMapper.map(product, productTypes);
                }).collect(Collectors.toList());
        CCATLogger.DEBUG_LOGGER.debug("Finished filtering retrieved products based on type");
        
        CCATLogger.DEBUG_LOGGER.info("Finished get main product service");
        return filteredProducts;
        
    }
}
