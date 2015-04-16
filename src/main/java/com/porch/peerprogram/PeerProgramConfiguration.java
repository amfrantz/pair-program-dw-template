package com.porch.peerprogram;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class PeerProgramConfiguration extends Configuration {
    @NotEmpty
    private String someConfigurableValue;

    @JsonProperty
    public String getSomeConfigurableValue() {
        return someConfigurableValue;
    }

    @JsonProperty
    public void setSomeConfigurableValue(String someConfigurableValue) {
        this.someConfigurableValue = someConfigurableValue;
    }
}
