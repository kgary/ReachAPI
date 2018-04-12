package edu.asu.heal.core.api.models;

import edu.asu.heal.core.api.resources.*;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import java.util.List;

public abstract class HEALResponse1 {
    public static final String ACTIVITY_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityResource.class).toTemplate().replace("/", "");
    public static final String ACTIVITY_INSTANCE_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityInstanceResource.class).toTemplate().replace("/", "");
    public static final String DOMAIN_RESOURCE_PATH = new JerseyUriBuilder().path(DomainResource.class).toTemplate().replace("/", "");
    public static final String PATIENT_RESOURCE_PATH = new JerseyUriBuilder().path(PatientResource.class).toTemplate().replace("/", "");
    public static final String TRIAL_RESOURCE_PATH = new JerseyUriBuilder().path(TrialsResource.class).toTemplate().replace("/", "");

    private int statusCode;
    private Object data;
    private String serverURI;

    public final Object toEntity(){
        return toEntity(this.data);
    }

    protected abstract String toEntity(String data);

    protected abstract String toEntity(List<?> data);

    protected abstract <T> String toEntity(T data);

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final Object getData() {
        return this.data;
    }

    public final String getServerURI() {
        return this.serverURI;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public final void setData(Object data) {
        this.data = data;
    }

    public final void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }
}
