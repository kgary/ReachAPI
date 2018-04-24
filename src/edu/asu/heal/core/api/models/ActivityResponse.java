package edu.asu.heal.core.api.models;

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
