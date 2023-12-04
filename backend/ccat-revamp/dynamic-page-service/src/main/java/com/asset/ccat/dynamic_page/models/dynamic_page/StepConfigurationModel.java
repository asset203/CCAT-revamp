package com.asset.ccat.dynamic_page.models.dynamic_page;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "stepType", include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProcedureConfigurationModel.class, name = "1"),
        @JsonSubTypes.Type(value = HttpConfigurationModel.class, name = "2")
})
public abstract class StepConfigurationModel {
}
