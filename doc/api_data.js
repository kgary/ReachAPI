define({ "api": [  {    "type": "post",    "url": "/activity",    "title": "Create Activity",    "name": "CreateActivity",    "group": "Activity",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "ActivityId",            "description": "<p>Id of the Activity</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "ActivityName",            "description": "<p>Name of the Activity</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "ActivityType",            "description": "<p>Type of the Activity</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Description",            "description": "<p>Description of the Activity</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "CanonicalOrder",            "description": "<p>Canonical Order of the Activity</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "MetaData",            "description": "<p>Meta Data of the Activity</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can post this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",    "groupTitle": "Activity",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  },  {    "type": "get",    "url": "/activityInstance/:id",    "title": "ActivityInstance Detail",    "name": "ActivityInstanceDetail",    "group": "ActivityInstance",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "id",            "description": "<p>ActivityInstance's Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "error": {      "fields": {        "4xx": [          {            "group": "4xx",            "type": "404",            "optional": false,            "field": "ActivityInstanceNotFound",            "description": "<p>Activity Instance cannot be found with <code>id</code></p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",    "groupTitle": "ActivityInstance"  },  {    "type": "post",    "url": "/activityInstance",    "title": "Create ActivityInstance",    "name": "CreateActivityInstance",    "group": "ActivityInstance",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "StartTime",            "description": "<p>Start Time of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "EndTime",            "description": "<p>End Time of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "UserSubmissionTime",            "description": "<p>User Submission Time of the ActivityInstance</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Status",            "description": "<p>The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Sequence",            "description": "<p>The sequence of the activities</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "ActivityTitle",            "description": "<p>The title of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Description",            "description": "<p>Description about the Activity Instance</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",    "groupTitle": "ActivityInstance"  },  {    "type": "delete",    "url": "/activityInstance/:id",    "title": "Delete ActivityInstance",    "name": "DeleteActivityInstance",    "group": "ActivityInstance",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "id",            "description": "<p>ActivityInstance's unique id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",    "groupTitle": "ActivityInstance"  },  {    "type": "get",    "url": "/activityInstance?patientPin={patientPin}&trialId={trialId}",    "title": "ActivityInstances",    "name": "GetActivityInstances",    "group": "ActivityInstance",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "patientPin",            "description": "<p>Patient's Unique Id</p>"          },          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "trialId",            "description": "<p>Trial's Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "404",            "optional": false,            "field": "ActivityInstancesNotFound",            "description": "<p>Activity Instances cannot be found</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",    "groupTitle": "ActivityInstance"  },  {    "type": "put",    "url": "activityInstance",    "title": "Update ActivityInstance",    "name": "UpdateActivityInstance",    "group": "ActivityInstance",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "StartTime",            "description": "<p>Start Time of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "EndTime",            "description": "<p>End Time of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "DateTime",            "optional": false,            "field": "UserSubmissionTime",            "description": "<p>User Submission Time of the ActivityInstance</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Status",            "description": "<p>The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Sequence",            "description": "<p>The sequence of the activities</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "ActivityTitle",            "description": "<p>The title of the Activity Instance</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Description",            "description": "<p>Description about the Activity Instance</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",    "groupTitle": "ActivityInstance"  },  {    "type": "post",    "url": "/patient",    "title": "Add Patient",    "name": "AddPatient",    "group": "Patient",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DeviceType",            "description": "<p>Type of Device used by the patient</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": true,            "field": "DeviceVersion",            "description": "<p>Version of the Device</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DateStarted",            "description": "<p>DateTime at which the patient has started the participation</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DateCompleted",            "description": "<p>DateTime at which the patient is expected to complete the trial</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Type",            "description": "<p>Patient Type: Child | Adult | Parent Proxy</p>"          },          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "StageId",            "description": "<p>Unique Trial's Stage Id</p>"          },          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "ParentPinId",            "description": "<p>Patient's Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can post this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",    "groupTitle": "Patient",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  },  {    "type": "get",    "url": "/patient/:id",    "title": "Patient Detail",    "name": "GetPatientDetail",    "group": "Patient",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "id",            "description": "<p>Patient's Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",    "groupTitle": "Patient",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          },          {            "group": "Error 4xx",            "type": "404",            "optional": false,            "field": "NotFound",            "description": "<p>The patient cannot be found</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  },  {    "type": "get",    "url": "/patient?trialId={id}",    "title": "Get Patients of Trial",    "name": "GetPatientsOfTrial",    "group": "Patient",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "id",            "description": "<p>Trial Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can get this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",    "groupTitle": "Patient",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          },          {            "group": "Error 4xx",            "type": "404",            "optional": false,            "field": "NotFound",            "description": "<p>The patient cannot be found</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  },  {    "type": "delete",    "url": "/patient/:id",    "title": "Delete Patient",    "name": "RemovePatient",    "group": "Patient",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "patientId",            "description": "<p>Unique Patient's Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can delete this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",    "groupTitle": "Patient",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          },          {            "group": "Error 4xx",            "type": "404",            "optional": false,            "field": "NotFound",            "description": "<p>The patient cannot be found</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  },  {    "type": "put",    "url": "/patient",    "title": "Update Patient",    "name": "UpdatePatient",    "group": "Patient",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DeviceType",            "description": "<p>Type of Device used by the patient</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": true,            "field": "DeviceVersion",            "description": "<p>Version of the Device</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DateStarted",            "description": "<p>DateTime at which the patient has started the participation</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "DateCompleted",            "description": "<p>DateTime at which the patient is expected to complete the trial</p>"          },          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "Type",            "description": "<p>Patient Type: Child | Adult | Parent Proxy</p>"          },          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "StageId",            "description": "<p>Unique Trial's Stage Id</p>"          },          {            "group": "Parameter",            "type": "Number",            "optional": false,            "field": "ParentPinId",            "description": "<p>Patient's Unique Id</p>"          }        ],        "Login": [          {            "group": "Login",            "type": "String",            "optional": false,            "field": "pass",            "description": "<p>Only logged in user can put this</p>"          }        ]      }    },    "version": "0.0.0",    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",    "groupTitle": "Patient",    "error": {      "fields": {        "Error 4xx": [          {            "group": "Error 4xx",            "type": "400",            "optional": false,            "field": "BadRequest",            "description": "<p>Bad Request Encountered</p>"          },          {            "group": "Error 4xx",            "type": "401",            "optional": false,            "field": "UnAuthorized",            "description": "<p>The Client must be authorized to access the resource</p>"          }        ],        "Error 5xx": [          {            "group": "Error 5xx",            "type": "500",            "optional": false,            "field": "InternalServerError",            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"          },          {            "group": "Error 5xx",            "type": "501",            "optional": false,            "field": "NotImplemented",            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"          }        ]      }    }  }] });
