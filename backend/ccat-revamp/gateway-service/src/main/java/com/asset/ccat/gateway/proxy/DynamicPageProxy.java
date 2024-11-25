package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.dynamic_page.DynamicPageModel;
import com.asset.ccat.gateway.models.admin.dynamic_page.HttpParameterModel;
import com.asset.ccat.gateway.models.admin.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.gateway.models.admin.dynamic_page.StepModel;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.*;
import com.asset.ccat.gateway.models.requests.admin.user.ExtractUsersProfilesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ExecuteDynamicPageStepRequest;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ViewDynamicPageRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.AddDynamicPageResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.ParseResponseParametersResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ViewDynamicPageResponse;
import com.asset.ccat.gateway.models.shared.DynamicPageStepOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class DynamicPageProxy {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Properties properties;

    @LogExecutionTime
    public ViewDynamicPageResponse viewDynamicPage(ViewDynamicPageRequest viewDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - viewDynamicPage()");
        BaseResponse<ViewDynamicPageResponse> response;
        try {
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "privilegeId:" + viewDynamicPageRequest.getPrivilegeId() + "]");
            CCATLogger.DEBUG_LOGGER.info("Start getting dynamic page view");
            Mono<BaseResponse<ViewDynamicPageResponse>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.VIEW)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(viewDynamicPageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ViewDynamicPageResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting dynamic page view " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting dynamic page view" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting dynamic page view ");
            CCATLogger.ERROR_LOGGER.error("Error while getting dynamic page view", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - viewDynamicPage()");
        return response.getPayload();
    }

    @LogExecutionTime
    public List<DynamicPageModel> getAllPages(GetAllDynamicPagesRequest getAllDynamicPagesRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - getAllPages()");
        BaseResponse<List<DynamicPageModel>> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start getting all dynamic page");
            Mono<BaseResponse<List<DynamicPageModel>>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.GET_ALL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getAllDynamicPagesRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<DynamicPageModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting all dynamic pages " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting all dynamic pages" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting all dynamic pages ");
            CCATLogger.ERROR_LOGGER.error("Error while getting all dynamic pages ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - getAllPages()");
        return response.getPayload();
    }

    @LogExecutionTime
    public DynamicPageModel getPage(GetDynamicPageRequest getDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - getPage()");
        BaseResponse<DynamicPageModel> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start getting all dynamic page");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + "pageId:" + getDynamicPageRequest.getPageId() + "]");
            Mono<BaseResponse<DynamicPageModel>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(getDynamicPageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<DynamicPageModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while getting dynamic page " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while getting dynamic page" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while getting dynamic page ");
            CCATLogger.ERROR_LOGGER.error("Error while getting dynamic page ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - getPage()");
        return response.getPayload();
    }

    @LogExecutionTime
    public AddDynamicPageResponse addPage(AddDynamicPageRequest addDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - addPage()");
        BaseResponse<AddDynamicPageResponse> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start adding dynamic page");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + addDynamicPageRequest + "]");
            Mono<BaseResponse<AddDynamicPageResponse>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(addDynamicPageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<AddDynamicPageResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while adding dynamic page " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding dynamic page" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding dynamic page ");
            CCATLogger.ERROR_LOGGER.error("Error while adding dynamic page ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - addPage()");
        return response.getPayload();
    }

    @LogExecutionTime
    public void updatePage(UpdateDynamicPageRequest updateDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - updatePage()");
        BaseResponse<Integer> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start updating dynamic page");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + updateDynamicPageRequest + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateDynamicPageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating dynamic page " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating dynamic page" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating dynamic page ");
            CCATLogger.ERROR_LOGGER.error("Error while updating dynamic page ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - updatePage()");
    }

    @LogExecutionTime
    public void deletePage(DeleteDynamicPageRequest deleteDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - deletePage()");
        BaseResponse<Integer> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start deleting dynamic page");
            CCATLogger.INTERFACE_LOGGER.info("request is [ pageID:" + deleteDynamicPageRequest.getPageId() + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteDynamicPageRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting dynamic page " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deleting dynamic page" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting dynamic page ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting dynamic page ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - deletePage()");
    }

    @LogExecutionTime
    public StepModel addStep(AddStepRequest addStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - addStep()");
        BaseResponse<StepModel> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start adding dynamic page step");
            CCATLogger.INTERFACE_LOGGER.info("request is [ pageID:" + addStepRequest.getPageId()
                    + " step: [" + addStepRequest.getStep() + "]" +
                    "]");
            Mono<BaseResponse<StepModel>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE_STEP
                            + Defines.WEB_ACTIONS.ADD)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(addStepRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<StepModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while adding dynamic page step " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while adding dynamic page step" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while adding dynamic page step ");
            CCATLogger.ERROR_LOGGER.error("Error while adding dynamic page step ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - addStep()");
        return response.getPayload();
    }

    @LogExecutionTime
    public StepModel updateStep(UpdateStepRequest updateStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - updateStep()");
        BaseResponse<StepModel> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start updating dynamic page step");
            CCATLogger.INTERFACE_LOGGER.info("request is [ pageID:" + updateStepRequest.getPageId()
                    + " step: [" + updateStepRequest.getStep() + "]" +
                    "]");
            Mono<BaseResponse<StepModel>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE_STEP
                            + Defines.WEB_ACTIONS.UPDATE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(updateStepRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<StepModel>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while updating dynamic page step " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while updating dynamic page step" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while updating dynamic page step ");
            CCATLogger.ERROR_LOGGER.error("Error while updating dynamic page step ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - updateStep()");
        return response.getPayload();
    }

    @LogExecutionTime
    public void deleteStep(DeleteStepRequest deleteStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - deleteStep()");
        BaseResponse response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start deleting dynamic page step");
            CCATLogger.INTERFACE_LOGGER.info("request is [ pageID:" + deleteStepRequest.getPageId()
                    + " stepID: " + deleteStepRequest.getStepId() +
                    "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE_STEP
                            + Defines.WEB_ACTIONS.DELETE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(deleteStepRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while deleting dynamic page step " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while deleting dynamic page step" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while deleting dynamic page step ");
            CCATLogger.ERROR_LOGGER.error("Error while deleting dynamic page step ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - deleteStep()");
    }

    @LogExecutionTime
    public void testDBConnection(TestDBConnectionRequest testDBConnectionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - testDBConnection()");
        BaseResponse response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start testing db connection");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + testDBConnectionRequest + "]");
            Mono<BaseResponse> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DATABASE_MANAGEMENT
                            + Defines.DYNAMIC_PAGE_SERVICE.TEST_DB_CONNECTION)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(testDBConnectionRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while testing db connection " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while testing db connection" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while testing db connection ");
            CCATLogger.ERROR_LOGGER.error("Error while testing db connection ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - testDBConnection()");
    }

    @LogExecutionTime
    public List<ProcedureParameterModel> parseQuery(ParseProcedureParametersRequest parseProcedureParametersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - parseQuery()");
        BaseResponse<List<ProcedureParameterModel>> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start parsing procedure query");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + parseProcedureParametersRequest + "]");
            Mono<BaseResponse<List<ProcedureParameterModel>>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DATABASE_MANAGEMENT
                            + Defines.DYNAMIC_PAGE_SERVICE.PARSE_QUERY)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(parseProcedureParametersRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ProcedureParameterModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while parsing procedure query " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while parsing procedure query" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while parsing procedure query ");
            CCATLogger.ERROR_LOGGER.error("Error while parsing procedure query ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - parseQuery()");
        return response.getPayload();
    }

    @LogExecutionTime
    public List<HttpParameterModel> httpParseQuery(ParseHttpParametersRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - httpParseQuery()");
        BaseResponse<List<HttpParameterModel>> response;
        try {
            String URI = properties.getDynamicPageServiceUrls()
                    + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                    + Defines.DYNAMIC_PAGE_SERVICE.HTTP_MANAGEMENT
                    + Defines.DYNAMIC_PAGE_SERVICE.PARSE_QUERY;

            CCATLogger.DEBUG_LOGGER.info("Start parsing http query over URL : "+URI);
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
            Mono<BaseResponse<List<HttpParameterModel>>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<HttpParameterModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while parsing http query " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while parsing http query" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while parsing http query ");
            CCATLogger.ERROR_LOGGER.error("Error while parsing http query ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - httpParseQuery()");
        return response.getPayload();
    }


    @LogExecutionTime
    public List<DynamicPageStepOutputModel> executeStep(ExecuteDynamicPageStepRequest executeDynamicPageStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - executeStep()");
        BaseResponse<List<DynamicPageStepOutputModel>> response;
        try {
            CCATLogger.DEBUG_LOGGER.info("Start executing dynamic page step");
            CCATLogger.INTERFACE_LOGGER.info("request is [" + executeDynamicPageStepRequest + "]" +
                    "]");
            Mono<BaseResponse<List<DynamicPageStepOutputModel>>> responseAsync = webClient.post()
                    .uri(properties.getDynamicPageServiceUrls()
                            + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                            + Defines.DYNAMIC_PAGE_SERVICE.DYNAMIC_PAGE_STEP
                            + Defines.WEB_ACTIONS.EXECUTE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(executeDynamicPageStepRequest))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<DynamicPageStepOutputModel>>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while executing dynamic page step " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while executing dynamic page step" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while executing dynamic page step ");
            CCATLogger.ERROR_LOGGER.error("Error while executing dynamic page step ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - executeStep()");
        return response.getPayload();
    }

    @LogExecutionTime
    public ParseResponseParametersResponse httpParseResponseParameters(ResponseParameterParsingRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageProxy - httpParseResponseParameters()");
        BaseResponse<ParseResponseParametersResponse> response;
        try {
            String URI = properties.getDynamicPageServiceUrls()
                    + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                    + Defines.DYNAMIC_PAGE_SERVICE.HTTP_MANAGEMENT
                    + Defines.DYNAMIC_PAGE_SERVICE.PARSE_Response_PARAMETERS;

            CCATLogger.DEBUG_LOGGER.info("Start parsing http Response Parameters over URL : "+URI);
            CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");


            Mono<BaseResponse<ParseResponseParametersResponse>> responseAsync = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<ParseResponseParametersResponse>>() {
                    }).log();
            response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while parsing http XML Parameters " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while parsing http XML Parameters" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
                    + ", statusCode: " + response.getStatusCode()
                    + ", payload: " + response.getPayload()
                    + "]");

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while parsing http Response Parameters ");
            CCATLogger.ERROR_LOGGER.error("Error while parsing http Response Parameters ", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPageProxy - httpParseResponseParameters()");
        return response.getPayload();
    }

    @LogExecutionTime
    public ResponseEntity<Resource> importDynamicPageSettings(ImportDynamicPageRequest importDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting Importing Dynamic Page Settings - call Dynamic Page Service");
        CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Proxy - importDynamicPageSettings()");
        ResponseEntity<Resource> response = null;
        try {
            String URI = properties.getDynamicPageServiceUrls()
                    + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                    + Defines.DYNAMIC_PAGE_SERVICE.DATABASE_MANAGEMENT
                    + Defines.WEB_ACTIONS.IMPORT;
            CCATLogger.DEBUG_LOGGER.debug("Calling Dynamic Page Service... With URI : "+URI);
            Mono<ResponseEntity<Resource>> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(importDynamicPageRequest))
                    .retrieve()
                    .toEntity(Resource.class);

            response = res.block();
            CCATLogger.DEBUG_LOGGER.info("Ended Importing Dynamic Page Settings - call Dynamic Page Service Successfully");
            CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Proxy - importDynamicPageSettings() ended Successfully");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Extracting import dynamic page settings ");
            CCATLogger.ERROR_LOGGER.error("Error while Extracting import dynamic page settings", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        return response;
    }

    @LogExecutionTime
    public BaseResponse exportDynamicPageSettings(ExportDynamicPageSettingRequest exportDynamicPageSettingRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting Export Dynamic Page Settings - call Dynamic Page Service");
        CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Proxy - exportDynamicPageSettings()");
        BaseResponse response = null;
        try {
            String URI = properties.getDynamicPageServiceUrls()
                    + Defines.DYNAMIC_PAGE_SERVICE.CONTEXT_PATH
                    + Defines.DYNAMIC_PAGE_SERVICE.DATABASE_MANAGEMENT
                    + Defines.WEB_ACTIONS.EXPORT;
            CCATLogger.DEBUG_LOGGER.debug("Calling Dynamic Page Service... With URI : "+URI);
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", exportDynamicPageSettingRequest.getFile().getResource());
            builder.part("requestId", exportDynamicPageSettingRequest.getRequestId());
            builder.part("sessionId", exportDynamicPageSettingRequest.getSessionId());
            builder.part("token", exportDynamicPageSettingRequest.getToken());
            Mono<BaseResponse> res = webClient.post()
                    .uri(URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(BaseResponse.class);

            response = res.block();
            if (response != null) {
                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                    CCATLogger.DEBUG_LOGGER.info("Error while Extracting import dynamic page settings " + response);
                    CCATLogger.DEBUG_LOGGER.error("Error while Extracting import dynamic page settings" + response);
                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
                }
            }
            CCATLogger.DEBUG_LOGGER.info("Ended Importing Dynamic Page Settings - call Dynamic Page Service Successfully");
            CCATLogger.DEBUG_LOGGER.debug("Dynamic Page Proxy - importDynamicPageSettings() ended Successfully");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while Extracting import dynamic page settings ");
            CCATLogger.ERROR_LOGGER.error("Error while Extracting import dynamic page settings", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, "dynamic-page-service[" + properties.getDynamicPageServiceUrls() + "]");
        }
        return response;
    }
}
