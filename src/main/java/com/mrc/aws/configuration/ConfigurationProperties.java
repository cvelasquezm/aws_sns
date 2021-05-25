package com.mrc.aws.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProperties {

    public static ConfigurationProperties INSTANCE = getInstance();
    private Properties properties;
    private String fileProperties = "application.properties";

    public ConfigurationProperties() {
        properties = new Properties();
        final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileProperties);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static ConfigurationProperties getInstance(){
        if (INSTANCE == null) {
            return new ConfigurationProperties();
        }
        return INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }


}
