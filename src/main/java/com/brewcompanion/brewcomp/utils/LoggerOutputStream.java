package com.brewcompanion.brewcomp.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LoggerOutputStream extends ByteArrayOutputStream {

    private final Logger logger;
    private final Level level;

    public LoggerOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void flush() throws IOException {
        String contents = toString("UTF-8");
        super.reset();
        if (!contents.isEmpty() && !contents.equals("\n") && !contents.equals("\r\n")) {
            logger.log(level, contents);
        }
    }
}