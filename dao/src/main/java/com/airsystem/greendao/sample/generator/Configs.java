package com.airsystem.greendao.sample.generator;

/**
 * @author Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class Configs {
    public static final String BASE_DIR = System.getProperty("user.dir").replace("\\", "/");
    public static final String OUTPUT_DIR = new StringBuilder(BASE_DIR).append("/app/src/main/java").toString();
}