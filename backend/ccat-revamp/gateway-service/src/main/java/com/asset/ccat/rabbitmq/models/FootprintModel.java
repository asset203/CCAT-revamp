package com.asset.ccat.rabbitmq.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author marwa.elshawarby
 */
public class FootprintModel implements Serializable {

	private static final long serialVersionUID = -3550896358505066759L;
	private Long id;
	private String requestId;
	private String pageName;
	private String tabName;
	private String actionName;
	private Date actionTime;
	private String actionType;
	private String userName;
	private String profileName;
	private String msisdn;
	private String status;
	private String errorMessage;
	private String errorCode;
	private String sessionId;
	private String machineName;
	private Integer sendSms;

	private List<FootprintDetailsModel> footPrintDetails;

	public FootprintModel() {
	}

	public FootprintModel(String requestId, String pageName,
						  String actionName, String actionType,
						  String userName, String profileName,
						  String msisdn, String status,
						  String errorMessage, String errorCode,
						  String sessionId, String machineName,
						  Integer sendSms) {
		populateRequestData(requestId, msisdn, sendSms);
		populateCachedData(pageName, actionName, actionType);
		populateTokenData(sessionId, userName, profileName, machineName);
		populateResponseData(errorCode, errorMessage, status);
	}

	public List<FootprintDetailsModel> getFootPrintDetails() {
		return footPrintDetails;
	}

	public void setFootPrintDetails(List<FootprintDetailsModel> footPrintDetails) {
		this.footPrintDetails = footPrintDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Integer getSendSms() {
		return sendSms;
	}

	public void setSendSms(Integer sendSms) {
		this.sendSms = sendSms;
	}

	public void populateRequestData(String requestId, String msisdn, Integer sendSms){
		this.requestId = requestId;
		this.sendSms = sendSms;
		this.msisdn = msisdn;
	}

	public void populateTokenData(String sessionId, String username, String profileName, String machineName){
		this.sessionId = sessionId;
		this.userName = username;
		this.profileName = profileName;
		this.machineName = machineName;
	}
	public void populateResponseData(String errorCode, String errorMessage, String status){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.status = status;
	}
	public void populateCachedData(String pageName, String actionName, String actionType){
		this.pageName = pageName;
		this.actionName = actionName;
		this.actionType= actionType;
	}
}
