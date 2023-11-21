package com.asset.ccat.dynamic_page.mappers;

import com.asset.ccat.dynamic_page.constants.ParameterTypes;
import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepViewModel;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ViewDynamicPageResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicPageResponseMapper {

    public ViewDynamicPageResponse map(DynamicPageModel pageModel) {

        ViewDynamicPageResponse viewResponse = new ViewDynamicPageResponse();
        viewResponse.setPageId(pageModel.getId());
        viewResponse.setPageName(pageModel.getPageName());
        viewResponse.setRequireSearch(pageModel.getRequireSubscriber());


        List<StepViewModel> steps = pageModel.getSteps().stream().map((step) -> {
            StepViewModel stepView = new StepViewModel();
            stepView.setStepId(step.getId());
            stepView.setStepName(step.getStepName());
            stepView.setStepOrder(step.getStepOrder());
            stepView.setStepType(step.getStepType());
            stepView.setIsHidden(step.getIsHidden());
            stepView.setStepInputParameters(new ArrayList<>());

            //Handling Stored Procedure Configurations
            if (step.getStepType().equals(StepTypes.PROCEDURE.type)) {
                ((ProcedureConfigurationModel) step.getStepConfiguration())
                        .getParameters().forEach((param) -> {
                            if (param.getParameterType().equals(ParameterTypes.IN.id)) {
                                //TODO: Handle menu input mapping
                                stepView.getStepInputParameters().add(param);
                            }
                        });
            }
            //Handling HTTP Configurations
            else if (step.getStepType().equals(StepTypes.HTTP.type)) {
                ((HttpConfigurationModel) step.getStepConfiguration())
                        .getParameters().forEach((param) -> {
                            if (param.getParameterType().equals(ParameterTypes.IN.id)) {
                                //TODO: Handle menu input mapping
                                stepView.getStepInputParameters().add(param);
                            }
                        });
            }

            return stepView;
        }).collect(Collectors.toList());

        viewResponse.setSteps(steps);

        return viewResponse;
    }
}
