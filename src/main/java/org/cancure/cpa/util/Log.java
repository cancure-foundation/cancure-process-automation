package org.cancure.cpa.util;

import org.apache.log4j.Logger;

public class Log {

	private static final Logger LOGGER = Logger.getLogger(Log.class);
	
	public static Logger getLogger(){
		return LOGGER;
	}
}
