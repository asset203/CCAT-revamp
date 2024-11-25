package com.asset.ccat.balancedisputemapperservice.models;

/**
 * @author Assem.Hassan
 */
public class ServiceOfferingPlanDescModel {

  private Integer planId;
  private String planDescription;

  public ServiceOfferingPlanDescModel() {
  }

  public ServiceOfferingPlanDescModel(Integer planId, String planDescription) {
    this.planId = planId;
    this.planDescription = planDescription;
  }

  public Integer getPlanId() {
    return planId;
  }

  public void setPlanId(Integer planId) {
    this.planId = planId;
  }

  public String getPlanDescription() {
    return planDescription;
  }

  public void setPlanDescription(String planDescription) {
    this.planDescription = planDescription;
  }
}
