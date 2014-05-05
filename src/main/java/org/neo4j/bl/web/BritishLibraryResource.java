package org.neo4j.bl.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class BritishLibraryResource
{
    public BritishLibraryResource(  ) {

    }

    @GET
    @Timed
    public HomeView index() {
        return new HomeView(  );
    }
}
