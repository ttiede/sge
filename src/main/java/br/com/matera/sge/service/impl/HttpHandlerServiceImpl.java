package br.com.matera.sge.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.service.HttpHandlerService;
import br.com.matera.sge.util.StaticsUtils;

@Service
public class HttpHandlerServiceImpl implements HttpHandlerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandlerServiceImpl.class);

	@Override
	public String handle(final String endpoint) throws ServiceException {
		LOGGER.debug("M=handle: {}", endpoint);
		try {
			URL url = new URL(endpoint);
			final HttpURLConnection httpURLConnection;
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.addRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.connect();
			String content;
			if (httpURLConnection.getResponseCode() < 400) {
				content = StaticsUtils.extractToString(httpURLConnection.getInputStream());
			} else {
				content = StaticsUtils.extractToString(httpURLConnection.getErrorStream());
			}
			return content;
		} catch (final Exception e) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
	}
}
