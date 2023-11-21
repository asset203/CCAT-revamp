package com.asset.ccat.nba_service.models.requests;

/**
 * @author Mahmoud Shehab
 */
public class AcceptGiftRequest extends BaseRequest {

  private String msisdn;
  private String giftShortCode;
  private String wlist;

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getGiftShortCode() {
    return giftShortCode;
  }

  public void setGiftShortCode(String giftShortCode) {
    this.giftShortCode = giftShortCode;
  }

  public String getWlist() {
    return wlist;
  }

  public void setWlist(String wlist) {
    this.wlist = wlist;
  }

  @Override
  public String toString() {
    return "AcceptGiftRequest{" +
        "msisdn='" + msisdn + '\'' +
        ", giftShortCode='" + giftShortCode + '\'' +
        ", wlist='" + wlist + '\'' +
        '}';
  }
}
