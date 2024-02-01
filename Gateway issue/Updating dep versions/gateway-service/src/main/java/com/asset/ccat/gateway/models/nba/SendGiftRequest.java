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
public class SendGiftRequest extends BaseRequest {

  private String msisdn;
  private String giftSeqId;
  private String wlist;

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getGiftSeqId() {
    return giftSeqId;
  }

  public void setGiftSeqId(String giftSeqId) {
    this.giftSeqId = giftSeqId;
  }

  public String getWlist() {
    return wlist;
  }

  public void setWlist(String wlist) {
    this.wlist = wlist;
  }
}
