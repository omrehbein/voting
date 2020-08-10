package br.com.omr.voting.infrastructure.log;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;

@Component
public class Log implements ILog {

	private final Logger logger;
	public Log() {
		this.logger = LogManager.getLogger(StackLocatorUtil.getCallerClass(2));
	}
	
	@Override
	public void info(String message, Object... params) {
		logger.info(message, params);
	}

	@Override
	public void trace(String message, Object... params) {
		logger.trace(message, params);
	}

	@Override
	public void debug(String message, Object... params) {
		logger.debug(message, params);
	}

	@Override
	public void error(String message, Object... params) {
		logger.error(message, params);
	}


}
