package br.com.matera.sge.service.impl;

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

	@Autowired
	private HttpHandlerService httpHandlerService;
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandlerServiceImpl.class);

	public Course retrieveCourseOfStudent(String studentDocument) {
		Course course = null;
		try {
			course = this.fillCourse(studentDocument);
			if (StaticsUtils.extractOnlyNumbers(course.getDocument())
					.equals(StaticsUtils.extractOnlyNumbers(studentDocument))) {
				return course;
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Course fillCourse(String studentDocument) throws ServiceException {
		final String url = "http://services.groupkt.com/state/get/br/all";
		String content;
		try {
			content = httpHandlerService.handle(url);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		// Mock
		content = "{\"cpf\": \"99999999999\",\"notas\": {\"disciplina_1\": 10,\"disciplina_2\": 8.4,\"disciplina_3\": 7.3,\"disciplina_4\": 7.4}}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Course course = mapper.readValue(content, Course.class);
			return course;
		} catch (final Exception e) {
			final String message = "erro durante a comunicacao";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
	}
}