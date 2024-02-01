package com.asset.ccat.gateway.models.admin.dynamic_page;

/**
 * @author assem.hassan
 */
public class ProcedureCursorMappingModel {

    private Integer id;
    private Integer parameterId;
    private String actualColumnName;
    private String displayColumnName;

    public ProcedureCursorMappingModel() {
    }

    public ProcedureCursorMappingModel(Integer id, Integer parameterId, String actualColumnName, String displayColumnName) {
        this.id = id;
        this.parameterId = parameterId;
        this.actualColumnName = actualColumnName;
        this.displayColumnName = displayColumnName;
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

    @Override
    public String toString() {
        return "ProcedureCursorMappingModel{" +
                "id=" + id +
                ", parameterId=" + parameterId +
                ", actualColumnName='" + actualColumnName + '\'' +
                ", displayColumnName='" + displayColumnName + '\'' +
                '}';
    }
}
