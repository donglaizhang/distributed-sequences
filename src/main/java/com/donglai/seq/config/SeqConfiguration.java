package com.donglai.seq.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SeqConfiguration {
    private static final String CONFIG_FILE = "sequences.properties";
    String storageType;
    String storageName;

    String awsRegion;

    public String getAwsRegion() {
        return awsRegion;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getStorageName() {
        return storageName;
    }

    private SeqConfiguration(){
    }
    private static SeqConfiguration config = null;
    public static SeqConfiguration getSeqConfig() {
        if (config == null) {
            initializeConfig();
        }
        return config;
    }

    private static void initializeConfig() {
        config = new SeqConfiguration();
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();

            inputStream = SeqConfiguration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + CONFIG_FILE + "' not found in the classpath");
            }

            config.storageType = prop.getProperty("storage.type");
            config.storageName = prop.getProperty("storage.name");
            config.awsRegion = prop.getProperty("aws.region");


        } catch (Exception e) {
            //TODO need throw e
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                //TODO need throw e
            }
        }
    }
}
