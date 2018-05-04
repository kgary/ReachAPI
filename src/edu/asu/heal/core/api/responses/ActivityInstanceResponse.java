package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.hal.HALBuilderApiHALHelperImpl;
import edu.asu.heal.core.api.models.IHealModelType;

import java.util.List;

public class ActivityInstanceResponse extends HEALResponse {

    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        List<ActivityInstance> activityInstances = (List<ActivityInstance>)(List<?>) data;
        return new HALBuilderApiHALHelperImpl()
                .getActivityInstancesJSON(activityInstances,
                        this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH,
                        this.getServerURI() + PATIENT_RESOURCE_PATH,
                        this.getServerURI() + ACTIVITY_RESOURCE_PATH);
    }

    @Override
    protected String toEntity(IHealModelType instance) {
        ActivityInstance activityInstance = (ActivityInstance) instance;
        System.out.println("ACTIVITY INSTANCE INSIDE HAL BUILDER");
        System.out.println(activityInstance);
        return new HALBuilderApiHALHelperImpl()
                .getActivityInstancesJSON(activityInstance,
                        this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH,
                        this.getServerURI() + PATIENT_RESOURCE_PATH,
                        this.getServerURI() + ACTIVITY_RESOURCE_PATH);
    }
}
