package com.porch.pairprogram;

import com.porch.pairprogram.resources.HoneyDoListResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PairProgramApplication extends Application<PairProgramConfiguration> {
    public static void main(String[] args) throws Exception {
        new PairProgramApplication().run(args);
    }

    @Override
    public String getName() {
        return "porch-pair-programming";
    }

    @Override
    public void initialize(Bootstrap<PairProgramConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(PairProgramConfiguration configuration,
                    Environment environment) {

        final HoneyDoListResource honeyDoResource = new HoneyDoListResource();
        environment.jersey().register(honeyDoResource);
    }
}
