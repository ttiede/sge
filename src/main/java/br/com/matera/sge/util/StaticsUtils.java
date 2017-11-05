package br.com.matera.sge.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class StaticsUtils {
	public static String extractToString(final InputStream inputStream) throws IOException {
        final StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, Charset.defaultCharset());
        return writer.toString();
    }
}
