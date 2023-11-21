package com.asset.ccat.balancedisputemapperservice.models.requests;

public class GetDetailsConfigMapRequest extends BaseRequest{

  private Integer profileId;

  public Integer getProfileId() {
    return profileId;
  }

  public void setProfileId(Integer profileId) {
    this.profileId = profileId;
  }
}
