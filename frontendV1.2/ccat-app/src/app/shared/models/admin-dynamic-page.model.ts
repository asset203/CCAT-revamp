export interface ShortDetailedDynamicPage {
    id: number;
    pageName: string;
}
export interface AdminDynamicPageInfo {
    pageName?: string;
    privilegeName?: string;
    requireSubscriber?: boolean;
    pageId?: number;
    privilegeId?: number;
}
export interface Paramter {
    id?: string;
    parameterName?: string;
    displayName?: string;
    displayOrder?: number;
    parameterDataType?: number;
    parameterType?: number;
    parameterOrder?: number;
    inputMethod?: number;
    defaultValue?: string;
    responseCode?: boolean;
    responseDescription?: boolean;
    sourceStepParameterId?: number;
    hidden?: boolean;
    dropdownList?: ParameterDropdown[];
    cursorParameterMappings?: CursorParameterMapping[];
    stepOrder?: number;
    valid?: boolean;
    httpResponseMappingModels?: HttpResponseMappingModels[];
    dateFormat?: string;
    sourceStepParameterName?: string;
    xPath?: string;
}
export interface Step {
    id?: number;
    pageId?: number;
    stepId?: number;
    stepName?: string;
    stepOrder?: number;
    stepType?: number;
    isHidden?: boolean;
    stepConfiguration?: StepConfigurationProceduce | StepConfigurationHttp;
}
export interface StepConfigurationHttp {
    id?: number;
    stepId?: number;
    httpURL?: string;
    httpMethod?: number;
    headers?: string;
    successCode?: string;
    maxRecords?: number;
    requestContentType: number;
    responseContentType: number;
    responseForm: number;
    requestBody: string;
    mainDelimiter: string;
    keyValueDelimiter: string;
    parameters?: Paramter[];

}
export interface HttpResponseMappingModels {
    id?: number;
    parameterId?: number;
    headerName?: string;
    headerDisplayName?: string;
}
export interface StepConfigurationProceduce {
    databasePassword?: string;
    databaseURL?: string;
    databaseUsername?: string;
    datasourceName?: string;
    id?: number;
    maxRecords?: number;
    procedureName?: string;
    stepId?: number;
    successCode?: string;
    parameters?: Paramter[];
    extraConfigurations?: string;
    schema?: string;
}

export interface ParameterDropdown {
    label: string;
    value: number;
}
export interface CursorParameterMapping {
    actualColumnName: string;
    displayColumnName: string;
    id?: number;
    parameterId?: number;
    displayOrder?: number;
    dataType?: number;
    dateFormat?: string;
}
