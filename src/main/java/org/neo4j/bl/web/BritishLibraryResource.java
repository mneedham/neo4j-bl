package org.neo4j.bl.web;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class BritishLibraryResource
{
    private Neo4jDatabase neo4j;

    public BritishLibraryResource( Neo4jDatabase neo4j )
    {
        this.neo4j = neo4j;
    }

    @GET
    @Timed
    public HomeView index()
    {
        HashMap<String, Object> properties = new HashMap<>();

        JsonNode result = neo4j.query( "MATCH (n) RETURN n LIMIT 5", properties );

        return new HomeView();
    }
}
