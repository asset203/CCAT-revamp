package com.asset.ccat.gateway.models.customer_care;

public class GetAllCommunitiesModel {

	private Integer communityId;
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getCommunityDescription() {
		return communityDescription;
	}
	public void setCommunityDescription(String communityDescription) {
		this.communityDescription = communityDescription;
	}
	private String communityDescription;
}
