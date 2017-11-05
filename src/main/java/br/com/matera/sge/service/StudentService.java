package br.com.matera.sge.service;

import br.com.matera.sge.model.Student;

public interface StudentService {

	public java.util.List<Student> retrieveAllStudents();

	public Student retrieveStudent(String studentDocument);
}