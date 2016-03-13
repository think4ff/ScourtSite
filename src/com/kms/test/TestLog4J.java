package com.kms.test;

import org.apache.log4j.Logger;

public class TestLog4J {

	protected static Logger logger = Logger.getLogger( TestLog4J.class.getName());

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//System.out.print("test");

		//DEBUG < INFO < WARN < ERROR < FATAL

		logger.fatal("log4j:logger.fatal()");

		logger.error("log4j:logger.error()");

		logger.warn("log4j:logger.warn()");

		logger.info("log4j:logger.info()");

		logger.debug("log4j:logger.debug()");
	}

}
