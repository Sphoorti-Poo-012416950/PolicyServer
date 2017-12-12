package edu.sjsu.cs249.happypatients.policyserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cs249.happypatients.connectors.RedisConnector;

@Path("/policy")
public class PolicyServer {
	private static RedisConnector redis= new RedisConnector("localhost", 6379);
	
	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPolicy() {
		String policy = redis.get("fetchpolicy");
		if(policy == null) {
			return setDefaultPolicy();
		} 
		
		return policy;
		
	}

	@PUT
	@Path("/set")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String SetPolicy(String policy) {
		redis.set("fetchpolicy", policy);
		return "Policy changed successfully";
	}
	
	private String setDefaultPolicy() {	
		Properties prop = new Properties();
		String policy = "";
		try {
			prop.load(new FileInputStream(new File(getClass().getClassLoader().getResource("config.properties").getFile())));
			policy = prop.getProperty("fetchpolicy");
			redis.set("fetchpolicy", policy);
			return policy;  		
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalServerErrorException();
		}
	}
}

