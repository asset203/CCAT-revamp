package com.asset.ccat.air_service.factories;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.parsers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 * @implNote : Parser Factory to Retrieve & Apply the correct Strategy (Parser) dynamically.
 */
@Component
public class ParserFactory {
    private final Map<String, Parser> parsers;

    @Autowired
    public ParserFactory(AccumulatorParser accumulatorParser, OfferParser offerParser,
                         DedicatedAccountParser dedicatedAccountParser, AccountFlagParser accountFlagParser,
                         ChargesParser chargesParser, ServiceOfferingParser serviceOfferingParser,
                         TransactionRecordsParser transactionRecordsParser, PamParser pamParser,
                         FamilyAndFriendsParser fafParser, CommunityAndInfoParser communityParser,
                         PromotionPlanParser promotionPlanParser, UsageCounterParser usageCounterParser) {
        parsers = new HashMap<>();
        parsers.put(AIRDefines.accumulatorInformation, accumulatorParser);
        parsers.put(AIRDefines.offerInformation, offerParser);
        parsers.put(AIRDefines.dedicatedAccountInformation, dedicatedAccountParser);
        parsers.put(AIRDefines.accountFlags, accountFlagParser);
        parsers.put(AIRDefines.CHARGES_RESULT_INFO, chargesParser);
        parsers.put(AIRDefines.serviceOfferings, serviceOfferingParser);
        parsers.put(AIRDefines.transactionRecords, transactionRecordsParser);
        parsers.put(AIRDefines.pamInformationList, pamParser);
        parsers.put(AIRDefines.fafInformationList, fafParser);
        parsers.put(AIRDefines.communityInformationCurrent, communityParser);
        parsers.put(AIRDefines.promotionPlanInformation, promotionPlanParser);
        parsers.put(AIRDefines.usageCounterUsageThresholdInformation, usageCounterParser);
    }

    public Parser getParser(String parserType) {
        return parsers.get(parserType);
    }

    public Map<String, Parser> getParsers(){
        return parsers;
    }
    public void parseAirResponse(String parserType, Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        Parser parser = getParser(parserType);
        if(parser != null)
            parser.parse(currentNode, responseStrArr);
    }
}
