package com.asset.ccat.lookup_service.models;

/**
 * @author Assem.Hassan
 */
public class LkBalanceDisputeDetailsConfigModel {

  private String columnName;
  private String displayName;
  private Integer columnOrder;
  private Integer privilegeId;


  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Integer getColumnOrder() {
    return columnOrder;
  }

  public void setColumnOrder(Integer columnOrder) {
    this.columnOrder = columnOrder;
  }

  public Integer getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(Integer privilegeId) {
    this.privilegeId = privilegeId;
  }
}
