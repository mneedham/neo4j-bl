package org.neo4j.bl.web;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class BritishLibraryApplication extends Application<BritishLibraryConfiguration>
{
    public static void main(String[] args) throws Exception {
        new BritishLibraryApplication().run( args );
    }

    @Override
    public String getName() {
        return "The Tube Graph";
    }

    @Override
    public void initialize(Bootstrap<BritishLibraryConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/"));
    }

    @Override
    public void run(BritishLibraryConfiguration configuration,Environment environment) {
        final BritishLibraryResource resource = new BritishLibraryResource();
        environment.jersey().register(resource);
    }

}
