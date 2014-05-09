package org.neo4j.bl.web;

import java.net.URI;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class BritishLibraryApplication extends Application<BritishLibraryConfiguration>
{
    public static void main( String[] args ) throws Exception
    {
        new BritishLibraryApplication().run( args );
    }

    @Override
    public String getName()
    {
        return "British Library Meta Data";
    }

    @Override
    public void initialize( Bootstrap<BritishLibraryConfiguration> bootstrap )
    {
        bootstrap.addBundle( new ViewBundle() );
        bootstrap.addBundle( new AssetsBundle( "/assets/" ) );
    }

    @Override
    public void run( BritishLibraryConfiguration configuration, Environment environment )
    {
        Neo4jDatabase neo4jDatabase = new Neo4jDatabase( URI.create( "http://localhost:7474" ) );

        final BritishLibraryResource resource = new BritishLibraryResource( neo4jDatabase );
        environment.jersey().register( resource );
    }

}
