package edu.sjsu.happypatients.policyserver;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("myresource")
public class MyResource {

	Properties prop = new Properties();
	InputStream input = null;
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	try {

    		input = new FileInputStream("C:\\Users\\sphoo_000\\Documents\\workspace-sts-3.9.1.RELEASE\\policyserver\\config.properties");

    		// load a properties file
    		prop.load(input);

    		// get the property value and print it out
    		return prop.getProperty("fetchpolicy");  		

    	} catch (Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
		return null;
      }
    }
    
    
    

