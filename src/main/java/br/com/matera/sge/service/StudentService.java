package br.com.matera.sge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.matera.sge.model.Student;

@Component
public class StudentService {

	private static List<Student> students = new ArrayList<>();

	static {
		// Initialize Data
		Student jose = new Student("999.999.999-99", "Jose da Silva", "Rua Silvio Santos, 55", "99999-999");
		Student joao = new Student("999.999.999-98", "João da Pedro", "Rua Americo de Carvalho, 154", "99123-989");
		Student carlos = new Student("999.999.999-99", "Carlos Jose", "Marcia De Almeida", "23123-999");

		students.add(jose);
		students.add(joao);
		students.add(carlos);

	}

	public List<Student> retrieveAllStudents() {
		return students;
	}

	public Student retrieveStudent(String studentDocument) {
		for (Student student : students) {
			if (student.getDocument().equals(studentDocument)) {
				return student;
			}
		}
		return null;
	}
}