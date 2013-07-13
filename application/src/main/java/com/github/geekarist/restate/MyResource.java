package com.github.geekarist.restate;

import java.io.IOException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {

	@GET
	@Produces("text/xml")
	public String getIt(String news) throws IOException {	
		URL resource = Resources.getResource("cotation.rss.xml");
		return Resources.toString(resource, Charsets.UTF_8);
	}
}
