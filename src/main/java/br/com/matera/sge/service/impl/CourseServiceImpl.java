package br.com.matera.sge.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	public Course retrieveCourseOfStudent(String studentDocument) throws JsonParseException, JsonMappingException, IOException {
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

	private Course fillCourse(String studentDocument) throws ServiceException, JsonParseException, JsonMappingException, IOException {
		final String url = "http://services.groupkt.com/state/get/br/all";
		String content;
		try {
			content = httpHandlerService.handle(url);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		// Mock
		content = "{\"cpf\": \"99999999999\",\"notas\": {\"disciplina_1\": 10,\"disciplina_2\": 8.4,\"disciplina_3\": 7.3,\"disciplina_4\": 5.4}}";
		ObjectMapper mapper = new ObjectMapper();
		Course course = mapper.readValue(content, Course.class);
		return course;
	}
}