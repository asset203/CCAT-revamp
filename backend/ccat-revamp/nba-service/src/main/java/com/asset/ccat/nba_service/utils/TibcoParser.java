package com.asset.ccat.nba_service.utils;

import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.models.requests.GiftModel;
import com.asset.ccat.nba_service.models.responses.GetAllTibcoGiftsResponse;
import com.asset.ccat.nba_service.models.responses.tibco.getAllGifts.CharacteristicsValue;
import com.asset.ccat.nba_service.models.responses.tibco.getAllGifts.CustomerMarketingProduct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class TibcoParser {

  public static List<GiftModel> parseAssignGiftResponse(GetAllTibcoGiftsResponse response)
      throws NBAException {
    try {
      ArrayList<GiftModel> gifts = new ArrayList<>();
      /*
       * giftSeqId >> customerMarketingProduct.id.value
       * totalIncentive >> CustomerMarketingProduct.parts.specification.characteristicsValue[characteristicName=’ totalIncentive’].value
       * incentive >> CustomerMarketingProduct.parts. specification. characteristicsValue[characteristicName=’ offerIncentiveList’].value
       * totalPendingInsentive >> CustomerMarketingProduct.parts. specification. characteristicsValue[characteristicName=’ pendingInsentive’].value
       * giftDescription >> CustomerMarketingProduct.parts. specification. characteristicsValue[characteristicName=’ LongScript_Agents_Assignment].value
       * salesScript1 >> ??
       * giftShortCode >> CustomerMarketingProduct.parts. specification. characteristicsValue[characteristicName=’ RedemptionCode].value

       */
      List<CustomerMarketingProduct> customerMarketingProducts = response.getCustomerMarketingProduct();
      for (CustomerMarketingProduct product : customerMarketingProducts) {
        String giftSeqId = product.getId().get(0).getValue();
        String totalIncentive = "";
        String incentive = "";
        String totalPendingIncentive = "";
        String giftDescription = "";
        String salesScript1 = "";
        String giftShortCode = "";
        String wList = "";

        ArrayList<CharacteristicsValue> characteristicsValues = product.getParts()
            .getSpecification().getCharacteristicsValue();
        for (CharacteristicsValue characteristicsValue : characteristicsValues) {
          switch (characteristicsValue.getCharacteristicName()) {
            case "totalIncentive":
              totalIncentive = characteristicsValue.getValue();
              break;
            case "offerIncentiveList":
              incentive = characteristicsValue.getValue();
              break;
            case "pendingIncentive":
              totalPendingIncentive = characteristicsValue.getValue();
              break;
            case "LongScript_AgentAssignment":
              giftDescription = characteristicsValue.getValue();
              break;
            case "RedemptionCode":
              giftShortCode = characteristicsValue.getValue();
              break;
            case "wList":
              wList = characteristicsValue.getValue();
              break;
          }
        }
        GiftModel gift = new GiftModel();

        gift.setGiftSeqId(giftSeqId);
        gift.setIncentive(incentive);
        gift.setGiftShortCode(giftShortCode);
        gift.setGiftDescription(giftDescription);
        gift.setSalesScript1(salesScript1);
        gift.setTotalIncentive(totalIncentive);
        gift.setTotalPendingInsentive(totalPendingIncentive);
        gift.setGiftStatus("0");
        gift.setwList(wList);

        gifts.add(gift);
      }

      return gifts;
    } catch (Exception ex) {
      throw new NBAException(ErrorCodes.ERROR.TIBCO_PARSING_FAIL,
          " Can't parsing Get-all Tibco gifts response : " + ex.getMessage());
    }
  }
}
