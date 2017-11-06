package br.com.matera.sge.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.HttpHandlerService;
import br.com.matera.sge.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private static List<Student> students = new ArrayList<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandlerServiceImpl.class);

	@Autowired
	private HttpHandlerService httpHandlerService;

	public List<Student> retrieveAllStudents() throws ServiceException {
		if (this.students.size() == 0) {
			this.fillStudent();
		}
		return students;
	}

	private void fillStudent() throws ServiceException {	
		final String url = "http://services.groupkt.com/state/get/br/all";
		String content;
		try {
			content = httpHandlerService.handle(url);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		
		// Mock
		content = "[{\"documento\":\"999.999.999-99\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"},{\"documento\":\"888.888.888-88\",\"nome\":\"Jose da Silva Jr\",\"endereco\":\"Rua Silvio Santos, 54\",\"cep\":\"99999-888\"},{\"documento\":\"325.353.222-38\",\"nome\":\"Tiago Tiede\",\"endereco\":\"Rua Maestro Zeferino, 132 apto 6\",\"cep\":\"18040-010\"}]";
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.students.addAll(Arrays.asList(mapper.readValue(content, Student[].class)));
		} catch (final Exception e) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
	}
}