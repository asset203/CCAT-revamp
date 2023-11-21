export const PROCEDURE_TYPE=1;
export const HTTP_TYPE=2

export const INPUT_PARAMETER_TYPE_VALUE = 1;
export const OUTPUT_PARAMETER_TYPE_VALUE = 2;

export const STRING_DATA_TYPE = 2;
export const NUMBER_DATA_TYPE = 1;
export const CURSOR_DATA_TYPE = 3;
export const DATE_DATA_TYPE = 4;
export const LIST_DATA_TYPE = 3;

export const FLAT_INPUT_METHOD = 1;
export const MENU_INPUT_METHOD = 2;
export const STATIC_INPUT_METHOD = 3;
export const OUTPUT_FROM_OTHER_STEP_INPUT_METHOD = 4;

export const GET_HTTP_METHOD = 1;
export const POST_HTTP_METHOD = 2;

export const JSON_CONTENT_TYPE = 1;
export const TEXT_CONTENT_TYPE = 2;
export const XML_CONTENT_TYPE = 3;

export const KEY_VALUE_PAIRS_HTTP_RESPONSE_FORM = 1;
export const MAIN_DELIMETER_VALUES_HTTP_RESPONSE_FORM = 2;
export const END_OF_LINE_DELIMETER_VALUES_HTTP_RESPONSE_FORM = 3;
export const procedureDataTypes = [
    {name: 'String', value: STRING_DATA_TYPE, disable: false},
    {name: 'Number', value: NUMBER_DATA_TYPE, disable: false},
    {name: 'Cursor', value: CURSOR_DATA_TYPE, disable: false},
    {name: 'Date', value: DATE_DATA_TYPE, disable: false},
];
export const httpDataTypes = [
    {name: 'String', value: STRING_DATA_TYPE, disable: false},
    {name: 'Number', value: NUMBER_DATA_TYPE, disable: false},
    {name: 'List', value: LIST_DATA_TYPE, disable: false},
    {name: 'Date', value: DATE_DATA_TYPE, disable: false},
];
export const procedureParamsTypes = [
    {name: 'In', value: INPUT_PARAMETER_TYPE_VALUE},
    {name: 'Out', value: OUTPUT_PARAMETER_TYPE_VALUE},
];
export const procedureParamInputMethod = [
    {name: 'Flat', value: FLAT_INPUT_METHOD, disable: false},
    {name: 'Menu', value: MENU_INPUT_METHOD, disable: false},
    {name: 'Static', value: STATIC_INPUT_METHOD, disable: false},
    {
        name: 'Output From Other Step',
        value: OUTPUT_FROM_OTHER_STEP_INPUT_METHOD,
        disable: false,
    },
];
export const httpMethods = [
    {name: 'GET', value: GET_HTTP_METHOD},
    {name: 'POST', value: POST_HTTP_METHOD},
];
export const contentTypes = [
    {name: 'JSON', value: JSON_CONTENT_TYPE},
    {name: 'TEXT', value: TEXT_CONTENT_TYPE},
    {name: 'XML', value: XML_CONTENT_TYPE},
];
export const httpResponseForms = [
    {name: 'Key Value Pairs', value: KEY_VALUE_PAIRS_HTTP_RESPONSE_FORM},
    {name: 'Main Delimeter Values', value: MAIN_DELIMETER_VALUES_HTTP_RESPONSE_FORM},
    {name: 'End Of Line Delimeter Values', value: END_OF_LINE_DELIMETER_VALUES_HTTP_RESPONSE_FORM},
];
export const fileExtentions={
    xml : 2,
    json : 1,
    xsd : 3
}