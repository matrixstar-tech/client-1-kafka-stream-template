package com.td.pwssp.kafka.stream.utility;

import org.slf4j.Logger;

public class LogHelper {
    private String key;
    private final Logger logger;

    public LogHelper(Logger logger){
        this.key = "Did not get key";
        this.logger = logger;
    }

    public void trace(String msg){
        logger.trace(key+" - " + msg);
    }

    public void debug(String msg){
        logger.debug(key+" - " + msg);
    }

    public void info(String msg){
        logger.info(key+" - " + msg);
    }

    public void warn(String msg){
        logger.warn(key+" - " + msg);
    }

    public void error(String msg){
        logger.error(key+" - " + msg);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
