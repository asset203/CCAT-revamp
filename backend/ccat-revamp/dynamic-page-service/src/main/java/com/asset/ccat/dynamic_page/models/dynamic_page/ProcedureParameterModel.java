package com.asset.ccat.dynamic_page.models.dynamic_page;

import java.util.List;

public class ProcedureParameterModel extends ParameterModel {

    private List<ProcedureCursorMappingModel> cursorParameterMappings;

    public List<ProcedureCursorMappingModel> getCursorParameterMappings() {
        return cursorParameterMappings;
    }

    public void setCursorParameterMappings(List<ProcedureCursorMappingModel> cursorParameterMappings) {
        this.cursorParameterMappings = cursorParameterMappings;
    }

    @Override
    public String toString() {
        return "ProcedureParameterModel{" +
                "cursorParameterMappings=" + cursorParameterMappings +
                '}';
    }
}
