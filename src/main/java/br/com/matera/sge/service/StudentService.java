package br.com.matera.sge.service;

import java.util.List;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Student;

public interface StudentService {

	List<Student> retrieveAllStudents() throws ServiceException;
}