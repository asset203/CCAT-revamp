package com.asset.ccat.dynamic_page.adapters;

import com.asset.ccat.dynamic_page.constants.HTTPContentType;
import com.asset.ccat.dynamic_page.constants.HTTPMethodType;
import com.asset.ccat.dynamic_page.constants.HTTPResponseFormType;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HTTPRequestWrapperModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.parsers.JsonParser;
import com.asset.ccat.dynamic_page.parsers.XmlParser;
import com.asset.ccat.dynamic_page.proxy.HttpStepProxy;
import com.asset.ccat.dynamic_page.utils.HttpUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class HttpAdapter {

    @Autowired
    private StepParameterUtil stepParameterUtil;

//    @Autowired
//    private ObjectMapper objectMapper;

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private HttpStepProxy httpStepProxy;

    public Map<String, Object> handleHttpRequest(HttpConfigurationModel config, HashMap<String, Object> inputParamVals) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start preparing HTTP request");
        HTTPRequestWrapperModel request = parseHTTPRequest(config, inputParamVals);

        CCATLogger.DEBUG_LOGGER.debug("Call HTTP Step Proxy");
        String responseString = httpStepProxy.sendHTTPRequest(request);
        CCATLogger.DEBUG_LOGGER.debug("HTTP Step Proxy returned with response [" + responseString + "]");

        Map<String, Object> responseMap = parseHTTPResponse(config, responseString);
        CCATLogger.DEBUG_LOGGER.debug("Finished parsing response string to map [" + responseMap + "]");

        return responseMap;
    }

    private HTTPRequestWrapperModel parseHTTPRequest(HttpConfigurationModel config, HashMap<String, Object> inputParametersVals) throws DynamicPageException {
        HTTPRequestWrapperModel requestWrapperModel = null;
        if (config.getHttpMethod().equals(HTTPMethodType.GET.id)) {
            CCATLogger.DEBUG_LOGGER.debug("Start preparing HTTP get request");
            requestWrapperModel = parseHTTPGetRequest(config, inputParametersVals);

        } else if (config.getHttpMethod().equals(HTTPMethodType.POST.id)) {
            CCATLogger.DEBUG_LOGGER.debug("Start preparing HTTP post request");
            requestWrapperModel = parseHTTPPostRequest(config, inputParametersVals);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("HTTP method [" + config.getHttpMethod() + "] is not supported");
            throw new DynamicPageException(ErrorCodes.ERROR.HTTP_METHOD_NOT_SUPPORTED, Defines.SEVERITY.ERROR);
        }
        return requestWrapperModel;
    }

    private HTTPRequestWrapperModel parseHTTPGetRequest(HttpConfigurationModel config, HashMap<String, Object> inputParametersVals) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start constructing HTTP get request wrapper");
        HTTPRequestWrapperModel requestWrapper = new HTTPRequestWrapperModel();
        requestWrapper.setHttpMethod(HTTPMethodType.GET);

        CCATLogger.DEBUG_LOGGER.debug("Start getting input parameters list");
        List<HttpParameterModel> inputParamtersList = stepParameterUtil.getHTTPInputParamList(config.getParameters());

        // preparing url and query parameters
        String url = config.getHttpURL().trim();
        CCATLogger.DEBUG_LOGGER.debug("HTTP url before param replacement >> " + url);

        // handle request input parameters
        if (inputParamtersList != null && !inputParamtersList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start replacing url query parameters");
            StringBuilder queryParams = new StringBuilder();
            boolean isFirstParam = true;

            for (HttpParameterModel inputParam : inputParamtersList) {
                Object val = inputParametersVals != null && inputParametersVals.get(inputParam.getParameterName()) != null ?
                        inputParametersVals.get(inputParam.getParameterName()) : null;

                if (val != null) {
                    if (isFirstParam) {
                        queryParams.append("?");
                        isFirstParam = false;
                    } else {
                        queryParams.append("&");
                    }

                    String paramValue = val.toString();
                    queryParams.append(inputParam.getParameterName())
                            .append("=")
                            .append(paramValue);
                }
            }

            url = url + queryParams;
            CCATLogger.DEBUG_LOGGER.debug("HTTP url after param replacement >> {}", url);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("No input parameters configured");
        }

        requestWrapper.setUrl(url);

        // preparing request headers
        if (config.getHeaders() != null && !config.getHeaders().isBlank()) {
            String headers = config.getHeaders();
            requestWrapper.setHeaders(httpUtils.parseRequestHeaders(headers));
        }
        CCATLogger.DEBUG_LOGGER.debug("Finished parsing HTTP get request");
        return requestWrapper;
    }

    private HTTPRequestWrapperModel parseHTTPPostRequest(HttpConfigurationModel config, HashMap<String, Object> inputParametersVals) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start constructing HTTP post request wrapper");
        HTTPRequestWrapperModel requestWrapper = new HTTPRequestWrapperModel();
        requestWrapper.setHttpMethod(HTTPMethodType.POST);

        CCATLogger.DEBUG_LOGGER.debug("Start getting input parameters list");
        List<HttpParameterModel> inputParamtersList = stepParameterUtil.getHTTPInputParamList(config.getParameters());

        // preparing url and query parameters
        String url = config.getHttpURL().trim();
        CCATLogger.DEBUG_LOGGER.debug("HTTP url  >> " + url);
        requestWrapper.setUrl(url);

        String requestBody = config.getRequestBody();
        CCATLogger.DEBUG_LOGGER.debug("pre-configured request body >> " + requestBody);

        if (inputParamtersList != null && !inputParamtersList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start handling request body & input parameters");

            if (requestBody != null && !requestBody.isBlank()) {
                requestBody = config.getRequestBody().trim();
                CCATLogger.DEBUG_LOGGER.debug("Start replacing input parameters in given body template [" + requestBody + "]");
                for (HttpParameterModel inputParam : inputParamtersList) {
                    //TODO: Handle input of type list
                    Object val = inputParametersVals != null && inputParametersVals.get(inputParam.getParameterName()) != null ?
                            inputParametersVals.get(inputParam.getParameterName()) : null;
                    requestBody = httpUtils.replaceInputValueInString(inputParam,
                            val,
                            requestBody);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Start constructing request body from input parameters");
                if (config.getRequestContentType().equals(HTTPContentType.JSON.id)) {
                    // json request body
                    CCATLogger.DEBUG_LOGGER.debug("Constructing JSON request body");
                    requestBody = httpUtils.getJsonRequestBody(inputParamtersList, inputParametersVals);
                } else if (config.getRequestContentType().equals(HTTPContentType.XML.id)) {
                    // TODO handle xml requests
                    CCATLogger.DEBUG_LOGGER.debug("Constructing XML request body");
                    requestBody = "";
                } else if (config.getRequestContentType().equals(HTTPContentType.TEXT.id)) {
                    // TODO handle plain text requests
                    CCATLogger.DEBUG_LOGGER.debug("Constructing plain text request body");
                    requestBody = "";
                }
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished handling request body & input parameters >> [" + requestBody + "]");
        }

        // setting request body
        requestWrapper.setRequestBody(requestBody);

        // preparing request headers
        if (config.getHeaders() != null && !config.getHeaders().isBlank()) {
            String headers = config.getHeaders();
            requestWrapper.setHeaders(httpUtils.parseRequestHeaders(headers));
        }
        CCATLogger.DEBUG_LOGGER.debug("Finished parsing HTTP get request");
        return requestWrapper;
    }

    private Map<String, Object> parseHTTPResponse(HttpConfigurationModel config, String responseString) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing response string [" + responseString + "] to map");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            if (config.getResponseContentType().equals(HTTPContentType.JSON.id)) {
                CCATLogger.DEBUG_LOGGER.debug("Parsing response of json string");
//                responseMap = objectMapper.readValue(responseString, Map.class);
                responseMap = jsonParser.parseJsonResponse(responseString, stepParameterUtil.getHTTPOutputParamList(config.getParameters()));
            } else if (config.getResponseContentType().equals(HTTPContentType.XML.id)) {
                //TODO handle xml response
                CCATLogger.DEBUG_LOGGER.debug("Parsing response of XML string");
                responseMap = xmlParser.parseXmlResponse(responseString, stepParameterUtil.getHTTPOutputParamList(config.getParameters()));
            } else if (config.getResponseContentType().equals(HTTPContentType.TEXT.id)) {
                CCATLogger.DEBUG_LOGGER.debug("Parsing response of plain-text string");
                if (config.getResponseForm().equals(HTTPResponseFormType.MAIN_DELIMITER_VALUES.id)) {
                    // response form is String of values seperated by configured delimiter or default [,]
                    CCATLogger.DEBUG_LOGGER.debug("Parsing plain-text string of CSV form");
                    String delimiter = "";
                    if (config.getMainDelimiter() != null && !config.getMainDelimiter().isBlank()) {
                        delimiter = config.getMainDelimiter().trim();
                        CCATLogger.DEBUG_LOGGER.debug("Using configured main delimiter [" + delimiter + "]");
                    } else {
                        delimiter = ",";
                        CCATLogger.DEBUG_LOGGER.debug("Main delimiter is not configured, using default delimiter [" + delimiter + "]");
                    }
                    String[] vals = responseString.split(delimiter);
                    CCATLogger.DEBUG_LOGGER.debug("response string = {} || split by delimiter [{}] || values = {}", vals);
                    for (int i = 1; i < vals.length; i++) {
                        responseMap.put(i + "", vals[i].trim());
                    }
                } else if (config.getResponseForm().equals(HTTPResponseFormType.KEY_VALUE_PAIRS.id)) {
                    // response form is String of key-value pairs seperated by configured delimiter or default [&]
                    CCATLogger.DEBUG_LOGGER.debug("Parsing plain-text string of Key-Value form");
                    String keyValueDelimeter = "";
                    String mainDelimeter=config.getMainDelimiter().trim();
                    if (config.getKeyValueDelimiter() != null && !config.getKeyValueDelimiter().isBlank()) {
                        keyValueDelimeter = config.getKeyValueDelimiter().trim();
                        CCATLogger.DEBUG_LOGGER.debug("Using configured key-value delimiter [" + keyValueDelimeter + "]");
                    } else {
                        keyValueDelimeter = "&";
                        CCATLogger.DEBUG_LOGGER.debug("Key-value delimiter is not configured, using default delimiter [" + keyValueDelimeter + "]");
                    }
                    String[] responseEntries = responseString.trim().split(mainDelimeter);
                    for (String entry : responseEntries) {
                        String[] keyVal = entry.trim().split(Pattern.quote(keyValueDelimeter));
                        if (keyVal.length >= 1) {
                            String value=null;
                            if(keyVal.length!=1){
                                value=keyVal[1].trim();
                            }
                            responseMap.put(keyVal[0].trim(), keyVal[0].trim() + "=" + value);
                        }
                    }
                } else if (config.getResponseForm().equals(HTTPResponseFormType.END_OF_LINE_DELIMITER_VALUES.id)) {
                    // response form is String of values seperated by end of line
                    CCATLogger.DEBUG_LOGGER.debug("Parsing plain-text string of end of line delimited form");
                    String[] vals = responseString.split("\r?\n|\r");
                    for (int i = 1; i < vals.length; i++) {
                        responseMap.put(i + "", vals[i].trim());
                    }
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Text Response form [" + config.getResponseForm() + "] is not supported");
                    throw new DynamicPageException(ErrorCodes.ERROR.HTTP_RESPONSE_FORM_NOT_SUPPORTED);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Response Type [" + config.getResponseContentType() + "] is not supported");
                throw new DynamicPageException(ErrorCodes.ERROR.HTTP_RESPONSE_TYPE_NOT_SUPPORTED);
            }
        } catch (DynamicPageException e) {
            throw e;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occured while parsing HTTP response string >> " + e.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Exception occured while parsing HTTP response string >> " + e.getMessage(), e);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_HTTP_RESPONSE_BODY_FAILED, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Finished parsing response string: {}", responseMap);
        return responseMap;
    }

}
