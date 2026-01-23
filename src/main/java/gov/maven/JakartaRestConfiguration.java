package gov.maven;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.*;

/**
 * Configures Jakarta RESTful Web Services for the application.
 *
 * @author Juneau
 */
@ApplicationPath("src")
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        // Add your resource classes here
        resources.add(gov.maven.resources.JakartaEE8Resource.class);

        return resources;
    }
}
