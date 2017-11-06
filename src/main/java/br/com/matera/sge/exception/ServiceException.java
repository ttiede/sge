package br.com.matera.sge.exception;

import org.apache.log4j.Logger;

public class ServiceException extends Exception {

	private static Logger logger = Logger.getLogger(ServiceException.class);

	public ServiceException(String message, Exception e) {
		logger.error(message, e);
	}
}
