package com.csselect.mlserver;

import com.csselect.game.FeatureSet;
import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

public class MLServerTests extends TestClass {

    private static final String DATASET = "populationGender";
    private static final String DATASET_STORAGE_PATH = System.getProperty("user.dir") + "target/CSSelect/populationGender";
    private MLServer mlServer;

    @Override
    public void setUp() {
        Configuration config = Injector.getInstance().getConfiguration();
        mlServer = new RESTMLServer(config);
        try {
            deleteFeatureSet();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void reset() {

    }

    @Test
    public void testVersion() throws IOException {
        Assert.assertEquals("0.1.5", mlServer.getVersion());
    }

    @Test
    public void testGetFeatures() throws IOException {
        FeatureSet featureSet = mlServer.getFeatures(DATASET);
        Assert.assertNotNull(featureSet);
        Assert.assertEquals(DATASET, featureSet.getIdentifier());
        FeatureSet featureSet1 = mlServer.getFeatures(DATASET);
        Assert.assertNotNull(featureSet1);
        Assert.assertSame(featureSet, featureSet1);
    }

    @Test
    public void testGetScore() throws IOException {
        Collection<Feature> features = new LinkedList<>();
        features.add(new Feature(1, "1"));
        features.add(new Feature(1, "2"));
        Assert.assertEquals(0.5473, mlServer.getScore(DATASET, features), Double.MIN_VALUE);
    }

    @Test
    public void validDatasetTest() {
        Assert.assertTrue(mlServer.isValidDataset(DATASET));
        Assert.assertFalse(mlServer.isValidDataset("test"));
    }

    @Test
    public void testIsOnline() {
        Assert.assertTrue(mlServer.isOnline());
    }

    private void deleteFeatureSet() throws IOException {
        if (new File(DATASET_STORAGE_PATH).exists()) {
            Files.walk(Paths.get(DATASET_STORAGE_PATH))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
