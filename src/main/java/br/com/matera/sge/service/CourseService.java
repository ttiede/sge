package br.com.matera.sge.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.matera.sge.model.Course;

public interface CourseService {

	Course retrieveCourseOfStudent(String studentDocument) throws JsonParseException, JsonMappingException, IOException;
}