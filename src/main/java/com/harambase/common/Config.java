package com.harambase.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.util.ResourceBundle;

public class Config {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("server");

    public final static String TEMP_FILE_PATH = resourceBundle.getString("tempFile.path");
    public final static String SERVER_IP = resourceBundle.getString("server.ip");
    public final static int SERVER_PORT = Integer.parseInt(resourceBundle.getString("server.port"));

    public static String serverPath = "";

    private final static Logger logger = LoggerFactory.getLogger(Config.class);

    static {
        try {
            serverPath =  ResourceUtils.getURL("classpath:").getPath();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
