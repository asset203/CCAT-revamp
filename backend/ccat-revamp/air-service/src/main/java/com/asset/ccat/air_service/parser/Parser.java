package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.exceptions.AIRServiceException;
import org.w3c.dom.Node;

import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 * @implNote Common goal: Parsing Air response. Each parsing behaviour is a strategy.
 */
public interface Parser {
    void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException;
}
