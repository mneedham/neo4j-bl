package org.neo4j.bl.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class BritishLibraryConfiguration extends Configuration
{
    @NotEmpty
    private String defaultName = "The Tube Graph";


    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
}
