package com.porch.peerprogram;

import com.porch.peerprogram.resources.HoneyDoListResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PeerProgramApplication extends Application<PeerProgramConfiguration> {
    public static void main(String[] args) throws Exception {
        new PeerProgramApplication().run(args);
    }

    @Override
    public String getName() {
        return "porch-peer-programming";
    }

    @Override
    public void initialize(Bootstrap<PeerProgramConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(PeerProgramConfiguration configuration,
                    Environment environment) {

        final HoneyDoListResource honeyDoResource = new HoneyDoListResource();
        environment.jersey().register(honeyDoResource);
    }
}
