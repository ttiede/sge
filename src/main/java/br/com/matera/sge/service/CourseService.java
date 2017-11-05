package br.com.matera.sge.service;

import br.com.matera.sge.model.Course;

public interface CourseService {

	Course retrieveCourseOfStudent(String studentDocument);
}