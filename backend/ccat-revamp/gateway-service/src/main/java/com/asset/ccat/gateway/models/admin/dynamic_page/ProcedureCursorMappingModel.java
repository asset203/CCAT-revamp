package com.asset.ccat.gateway.models.admin.dynamic_page;

/**
 * @author assem.hassan
 */
public class ProcedureCursorMappingModel {

    private Integer id;
    private Integer parameterId;
    private String actualColumnName;
    private String displayColumnName;
    private Integer dataType;
    private String dateFormat;
    private Integer displayOrder;


    public ProcedureCursorMappingModel() {
    }

    public ProcedureCursorMappingModel(Integer id, Integer parameterId, String actualColumnName, String displayColumnName ,  Integer dataType, Integer displayOrder, String dateFormat) {
        this.id = id;
        this.parameterId = parameterId;
        this.actualColumnName = actualColumnName;
        this.displayColumnName = displayColumnName;
        this.dataType = dataType;
        this.displayOrder = displayOrder;
        this.dateFormat = dateFormat;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getActualColumnName() {
        return actualColumnName;
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
    }

    public String getDisplayColumnName() {
        return displayColumnName;
    }

    public void setDisplayColumnName(String displayColumnName) {
        this.displayColumnName = displayColumnName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }


    @Override
    public String toString() {
        return "ProcedureCursorMappingModel{" +
                "id=" + id +
                ", parameterId=" + parameterId +
                ", actualColumnName='" + actualColumnName + '\'' +
                ", displayColumnName='" + displayColumnName + '\'' +
                ", dataType=" + dataType +
                ", dateFormat='" + dateFormat + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
