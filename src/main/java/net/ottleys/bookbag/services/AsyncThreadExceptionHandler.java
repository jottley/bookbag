package net.ottleys.bookbag.services;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncThreadExceptionHandler implements AsyncUncaughtExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(AsyncThreadExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        logger.error("Exception message - " + ex.getMessage());
        logger.error("Method name - " + method.getName());
        for (Object param : params) {
            logger.error("Parameter value - " + param);
        }

        if (logger.isErrorEnabled()) {
            ex.printStackTrace();
        }
    }

}