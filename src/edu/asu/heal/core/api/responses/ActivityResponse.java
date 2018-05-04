package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.IHealModelType;

import java.util.List;

public class ActivityResponse extends HEALResponse {
    @Override
    protected String toEntity(String data) {
        return data;
    }

    @Override
    protected String toEntity(List<IHealModelType> data) {
        List<Activity> activities = (List<Activity>)(List<?>) data;
        return HALHelperFactory
                .getHALGenerator()
                .getActivitiesJSON(activities, this.getServerURI() + ACTIVITY_RESOURCE_PATH);

    }

    @Override
    protected String toEntity(IHealModelType data) {
        Activity a = (Activity) data;
        return HALHelperFactory
                .getHALGenerator()
                .getActivitiesJSON(a, this.getServerURI() + ACTIVITY_RESOURCE_PATH);
    }
}
