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

	private final List<Student> students = new ArrayList<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final HttpHandlerService httpHandlerService;

	@Autowired
	public StudentServiceImpl(HttpHandlerService httpHandlerService) {
		this.httpHandlerService = httpHandlerService;
	}

	public List<Student> retrieveAllStudents() throws ServiceException {
		if (this.students.size() == 0) {
			this.fillStudent();
		}
		return students;
	}

	private void fillStudent() throws ServiceException {	
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.students.addAll(Arrays.asList(mapper.readValue(getAllStudents(), Student[].class)));
		} catch (final Exception e) {
			final String message = "Error when try connection";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
	}
	
	public String getAllStudents() throws ServiceException {
		final String url = "http://localhost:8080/alunos";
		String content = null;
		try {
			content = httpHandlerService.handle(url);
		} catch (Exception e1) {
			final String message = "Error when try connection";
			LOGGER.error("M=handle: {}", message, e1);
			throw new ServiceException(message, e1);
		}
		return content;
	}
}