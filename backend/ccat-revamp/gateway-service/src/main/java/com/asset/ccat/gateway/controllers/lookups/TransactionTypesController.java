package com.asset.ccat.gateway.controllers.lookups;

import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.models.requests.TransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.TransactionTypeRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.shared.ADMTransactionCode;
import com.asset.ccat.gateway.models.shared.ADMTransactionType;
import com.asset.ccat.gateway.services.LookupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.TRANSACTIONS)
public class TransactionTypesController {

    @Autowired
    private LookupsService lookupService;

    @RequestMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<List<ADMTransactionType>>> getTransactionType(HttpServletRequest req,
                                                                                     @RequestBody TransactionTypeRequest transactionTypeRequest) throws AuthenticationException, Exception {
        List<ADMTransactionType> list = lookupService.getTransactiontypes(transactionTypeRequest.getFeatureId());

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                list), HttpStatus.OK);
    }

    @RequestMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<List<ADMTransactionCode>>> getTransactionCodes(HttpServletRequest req,
                                                                                      @RequestBody TransactionCodeRequest transactionCodeRequest) throws AuthenticationException, Exception {
        List<ADMTransactionCode> list = lookupService.getTransactionCodes(transactionCodeRequest.getTypeId());

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                list), HttpStatus.OK);
    }
}
