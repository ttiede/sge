package br.com.matera.sge.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.HttpHandlerService;
import br.com.matera.sge.util.StaticsUtils;

@Service
public class CourseServiceImpl implements CourseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

	private final HttpHandlerService httpHandlerService;

	@Autowired
	public CourseServiceImpl(HttpHandlerService httpHandlerService) {
		this.httpHandlerService = httpHandlerService;
	}

	public Course retrieveCourseOfStudent(String studentDocument) throws ServiceException {
		try {
			Course course = this.fillCourse(studentDocument);
			if (StaticsUtils.extractOnlyNumbers(course.getDocument())
					.equals(StaticsUtils.extractOnlyNumbers(studentDocument))) {
				return course;
			}
		} catch (Exception e) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);

		}
		return null;
	}

	private Course fillCourse(String studentDocument) throws ServiceException{
		final String url = "http://localhost:8080/"+studentDocument+"/notas";
		String content;
		try {
			content = httpHandlerService.handle(url);
		} catch (Exception e1) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e1);
			throw new ServiceException(message, e1);
		}
		// Mock
		content = "{\"cpf\": \"99999999999\",\"notas\": {\"disciplina_1\": 10,\"disciplina_2\": 8.4,\"disciplina_3\": 7.3,\"disciplina_4\": 5.4}}";
		ObjectMapper mapper = new ObjectMapper();
		Course course;
		try {
			course = mapper.readValue(content, Course.class);
		} catch (IOException e) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
		return course;
	}
}