# ReachAPI

Instructions to run the API. For now the server is already deployed on a linux server provided by the sponsor.

However, in order to run the API from scratch, pull this branch from this repository and run it on Intellij Idea (recommended) or any other IDE which supports RESTful Web Services and Web Application Frameworks in Java . You'll need to add JAR dependencies to this project. The JARs are in the lib directory inside the repository.

Once you're done adding the dependencies to the project. You'll need to configure the IDE to generate a WAR file as output upon building.

Once the WAR is generated, deploy (copy & paste) the WAR file on a Tomcat Server (recommended) or any other server which is capable of serving Java-servlet based applications.

The recommended context name for the API is ReachAPI. Once the context is completely deployed on a server. You can access endpoints of the API using 
`http://{server_address}}/ReachAPI/rest/{endpoint_name}`
