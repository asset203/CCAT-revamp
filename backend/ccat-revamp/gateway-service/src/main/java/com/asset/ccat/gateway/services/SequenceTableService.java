/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.proxy.SequenceTableProxy;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class SequenceTableService {

    @Autowired
    private SequenceTableProxy lookupSequenceProxy;

    public Map<String, List<Integer>> getDedAccountSequence() throws GatewayException {
        return lookupSequenceProxy.getDedAccountSequence();
    }

    public Map<String, List<Integer>> getAccumulatorSequence() throws GatewayException {
        return lookupSequenceProxy.getAccumulatorSequence();
    }
}
