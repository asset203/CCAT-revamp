package com.asset.ccat.air_service.models.requests;

import com.asset.ccat.air_service.models.CommunitiesModel;

import java.util.ArrayList;

public class CommunitiesRequest extends SubscriberRequest{
	
	private ArrayList<CommunitiesModel> currentList;
	private ArrayList<CommunitiesModel> newList;

	public ArrayList<CommunitiesModel> getCurrentList() {
		return currentList;
	}

	public void setCurrentList(ArrayList<CommunitiesModel> currentList) {
		this.currentList = currentList;
	}

	public ArrayList<CommunitiesModel> getNewList() {
		return newList;
	}

	public void setNewList(ArrayList<CommunitiesModel> newList) {
		this.newList = newList;
	}

	@Override
	public String toString() {
		return "CommunitiesRequest{" +
				"currentList=" + currentList +
				", newList=" + newList +
				'}';
	}
}
