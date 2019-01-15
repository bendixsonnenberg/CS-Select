package com.csselect.mlserver;

import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.Random;

/**
 * Mock-Implementation of the {@link com.csselect.mlserver.MLServer} Interface
 */
public class MockMLServer implements MLServer {

    private Random random;

    @Inject
    MockMLServer() {
        this.random = new Random();
    }

    @Override
    public String getVersion() {
        return "0.1.5";
    }

    //TODO Populate FeatureSet with Features when the needed classes are available
    @Override
    public FeatureSet getFeatures(String dataset) {
        return new FeatureSet(dataset);
    }

    @Override
    public double getScore(String dataset, Collection<Feature> selectedFeatures) {
        return random.nextDouble();
    }
}
