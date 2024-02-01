package com.asset.ccat.gateway.mappers;

import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlan;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanBitModel;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlanNameModel;
import com.asset.ccat.gateway.models.customer_care.ServiceOfferingPlanModel;
import com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models.ServiceOfferingBitDetailsModel;
import com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models.ServiceOfferingPlanBitDetailsModel;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.GetServiceOfferingsRequest;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlanBitsResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlansResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Component
public class ServiceOfferingPlanResponseMapper {


    public GetAllServiceOfferingPlansResponse mapGetAllServiceOfferingPlanNameResponse(List<ServiceOfferingPlan> serviceOfferingPlans) {
        GetAllServiceOfferingPlansResponse response = new GetAllServiceOfferingPlansResponse();

        List<ServiceOfferingPlanNameModel> list = new ArrayList<>();

        serviceOfferingPlans.forEach(serviceOfferingPlan -> {
            ServiceOfferingPlanNameModel s = new ServiceOfferingPlanNameModel();
            s.setPlanId(serviceOfferingPlan.getPlanId());
            s.setName(serviceOfferingPlan.getName());
            list.add(s);
        });
        response.setServiceOfferingPlans(list);
        return response;
    }


//    public GetAllServiceClassPlanDescriptionResponse mapGetAllServiceOfferingServiceClassDescriptionResponse(ServiceOfferingPlan serviceOfferingPlan) {
//        GetAllServiceClassPlanDescriptionResponse response = new GetAllServiceClassPlanDescriptionResponse();
//        response.setPlanId(serviceOfferingPlan.getPlanId());
//        response.setPlanName(serviceOfferingPlan.getPlanName());
//        List<SOServiceClassDescriptionModel> list = new ArrayList<>();
//
//        if (serviceOfferingPlan.getSoServiceClassDescriptions() != null)
//            serviceOfferingPlan.getSoServiceClassDescriptions().values().forEach(serviceClassPlanDes ->
//            {
//
//                SOServiceClassDescriptionModel s = new SOServiceClassDescriptionModel();
//                s.setDescription(serviceClassPlanDes.getDescription());
//                s.setServiceClassId(serviceClassPlanDes.getServiceClassId());
//                list.add(s);
//            });
//
//
//        response.setSoServiceClassDescriptions(list);
//        return response;
//    }


    public GetAllServiceOfferingPlanBitsResponse extractServiceOfferingPlanBits(ServiceOfferingPlan serviceOfferingPlan) {
        GetAllServiceOfferingPlanBitsResponse response = new GetAllServiceOfferingPlanBitsResponse();
        response.setPlanId(serviceOfferingPlan.getPlanId());
        response.setPlanName(serviceOfferingPlan.getName());
        List<ServiceOfferingPlanBitModel> list = new ArrayList<>();

        if (serviceOfferingPlan.getServicePlanBits() != null)
            serviceOfferingPlan.getServicePlanBits().forEach((key, value) -> {
                ServiceOfferingPlanBitModel s = new ServiceOfferingPlanBitModel();
                s.setBitPosition(key);
                s.setIsEnabled(value);
                list.add(s);
            });
        response.setPlanBits(list);
        return response;
    }
    public List<ServiceOfferingPlanModel> serviceOfferingDetailsMapToServiceOfferingPlanList(HashMap<Integer, ServiceOfferingPlanBitDetailsModel> map,
                                                                                             GetServiceOfferingsRequest request) {
        CCATLogger.DEBUG_LOGGER.debug("serviceOfferingDetailsMapToServiceOfferingPlanList() :Start Mapping");

        List<ServiceOfferingPlanModel> list = new ArrayList<>();

        if(Objects.nonNull(map) && !map.isEmpty()){
            map.forEach((planId, planDetailsModel) -> {
                ServiceOfferingPlanModel planModel = new ServiceOfferingPlanModel();
                //Setting Plan ID & Plan Name based on serviceClassId
                planModel.setId(planId);
                HashMap<Integer,String> serviceClassDescForPlans = planDetailsModel.getServiceClassDisc();
                if(serviceClassDescForPlans.isEmpty()){
                    planModel.setName(planDetailsModel.getPlanName());
                }else if (serviceClassDescForPlans.containsKey(request.getServiceClassId())){
                    planModel.setName(serviceClassDescForPlans.get(request.getServiceClassId()));
                }else {
                    planModel.setName(planDetailsModel.getPlanName());
                }

                //Setting List Of Bits
                List<ServiceOfferingPlanBitModel> bitsList = new ArrayList<>();
                HashMap<Integer, ServiceOfferingBitDetailsModel> bitModelMap = planDetailsModel.getBitModelHashMap();
                if(Objects.nonNull(bitModelMap) && !bitModelMap.isEmpty()){
                    bitModelMap.forEach((bitPosition, bitDetailsModel) -> {
                        ServiceOfferingPlanBitModel bitModel = new ServiceOfferingPlanBitModel();
                        bitModel.setIsEnabled(bitDetailsModel.getIsEnabled());
                        bitModel.setBitPosition(bitDetailsModel.getBitPosition());
                        HashMap<Integer,String> serviceClassDescForBits = bitDetailsModel.getServiceClassDesc();
                        if(serviceClassDescForBits.isEmpty()){
                            bitModel.setBitName(bitDetailsModel.getBitName());
                        }else if (serviceClassDescForBits.containsKey(request.getServiceClassId())){
                            bitModel.setBitName(serviceClassDescForBits.get(request.getServiceClassId()));
                        }else {
                            bitModel.setBitName(bitDetailsModel.getBitName());
                        }

                        String profileSOB = request.getProfileSOB() == null? "" :request.getProfileSOB();
                        if(profileSOB != null && profileSOB.isBlank()){
                            profileSOB = profileSOB.trim();
                            profileSOB = profileSOB.startsWith(",") ? profileSOB : "," + profileSOB;
                            profileSOB = profileSOB.endsWith(",") ? profileSOB : profileSOB + ",";
                        }
                        if(profileSOB.isEmpty() || profileSOB.contains("," + bitModel.getBitPosition() + ",")){
                            bitModel.setDisabled(false);
                        }else{
                            bitModel.setDisabled(true);
                        }
                        bitsList.add(bitModel);
                    });
                }

                planModel.setBits(bitsList);
                list.add(planModel);
            });
        }
        CCATLogger.DEBUG_LOGGER.debug("serviceOfferingDetailsMapToServiceOfferingPlanList() :End Mapping Successfully");

        return list;
    }
}
