package com.td.pwssp.kafka.stream.utility;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelperTest {
    private static final Logger logger = LoggerFactory.getLogger(LogHelperTest.class);
    @Test
    void testLog(){

        LogHelper lh = new LogHelper(logger);
        lh.setKey("1");
        String returnValue = "Test";
        lh.trace("return value: " +returnValue);
    }
}
