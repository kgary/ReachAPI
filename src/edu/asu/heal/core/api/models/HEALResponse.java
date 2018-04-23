package edu.asu.heal.core.api.models;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.asu.heal.core.api.resources.*;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import java.util.List;

public class HEALResponse {

    private static final String ACTIVITY_RESOURCE_PATH;
    private static final String ACTIVITY_INSTANCE_RESOURCE_PATH;
    private static final String DOMAIN_RESOURCE_PATH;
    private static final String PATIENT_RESOURCE_PATH;
    private static final String TRIAL_RESOURCE_PATH;

    static{

        ACTIVITY_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityResource.class).toTemplate().replace("/", "");
        ACTIVITY_INSTANCE_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityInstanceResource.class).toTemplate().replace("/", "");
        DOMAIN_RESOURCE_PATH = new JerseyUriBuilder().path(DomainResource.class).toTemplate().replace("/", "");
        PATIENT_RESOURCE_PATH = new JerseyUriBuilder().path(PatientResource.class).toTemplate().replace("/", "");
        TRIAL_RESOURCE_PATH = new JerseyUriBuilder().path(TrialsResource.class).toTemplate().replace("/", "");
    }

    private int statusCode;
    private Object data;
    private String serverURI;

    private HEALResponse() {
    }

    // getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }

    public String getServerURI() {
        return serverURI;
    }

}