package br.com.matera.sge.service;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;

public interface CourseService {

	Course retrieveCourseOfStudent(String studentDocument) throws ServiceException;
	String getCourseByStudente(String studentDocument) throws ServiceException;
}