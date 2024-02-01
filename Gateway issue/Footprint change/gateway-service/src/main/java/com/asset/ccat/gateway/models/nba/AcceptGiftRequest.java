/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.nba;

import com.asset.ccat.gateway.models.requests.BaseRequest;

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
}
