package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.PamInformationModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */

@Service
public class PamParser implements Parser {
    private final AIRUtils aIRUtils;
    private final LookupsService lookupsService;

    @Autowired
    public PamParser(AIRUtils aIRUtils, LookupsService lookupsService) {
        this.aIRUtils = aIRUtils;
        this.lookupsService = lookupsService;
    }

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing PAM.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        List<PamInformationModel> pamInformationList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                PamInformationModel pamModel = new PamInformationModel();
                processValueNode(currentChild, pamModel);
                pamInformationList.add(pamModel);
            }
        }
        responseStrArr.put(AIRDefines.pamInformationList, pamInformationList);
    }


    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, PamInformationModel pamModel) throws AIRServiceException {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, pamModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, PamInformationModel pamModel) throws AIRServiceException {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, pamModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, PamInformationModel pamModel) throws AIRServiceException {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.pamServiceID:
                pamModel.setPamServiceID(value);
                break;
            case AIRDefines.pamClassID:
                Integer classId = Integer.parseInt(value);
                pamModel.setPamClassID(classId);
                pamModel.setPamClassDesc(lookupsService.getPamClasses().get(classId) == null ? value :
                        lookupsService.getPamClasses().get(classId).getDescription());
                break;
            case AIRDefines.scheduleID:
                Integer scheduleId = Integer.parseInt(value);
                pamModel.setPamScheduleID(scheduleId);
                pamModel.setPamScheduleDesc(lookupsService.getPamSchedule().get(scheduleId) == null ? value :
                        lookupsService.getPamClasses().get(scheduleId).getDescription());
                break;
            case AIRDefines.currentPamPeriod:
                pamModel.setCurrentPamPeriod(value);
                break;
            case AIRDefines.deferredToDate:
                pamModel.setDeferredToDate(aIRUtils.formatAir2CCDateTime(value));
                break;
            case AIRDefines.lastEvaluationDate:
                pamModel.setLastEvaluationDate(aIRUtils.formatAir2CCDateTime(value));
                break;
            case AIRDefines.pamServicePriority:
                pamModel.setPamServicePriority(value);
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

}
