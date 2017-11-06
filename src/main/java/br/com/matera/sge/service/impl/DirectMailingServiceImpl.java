package br.com.matera.sge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;
import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.DirectMailingService;
import br.com.matera.sge.service.StudentService;

@Service
public class DirectMailingServiceImpl implements DirectMailingService {

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	@Override
	public List<DirectMailing> retrieveElegibleStudents(List<DirectMailing> mailings)
			throws JsonParseException, JsonMappingException, IOException {
		List<DirectMailing> mailingsElegileStudents = new ArrayList();
		try {
			List<Student> students = studentService.retrieveAllStudents();

			for (final DirectMailing directMailing : mailings) {

				List<Student> elegileStudent = students.stream()
						.filter(s -> s.getName().equals(directMailing.getName())
								&& s.getAddress().equals(directMailing.getAddress())
								&& s.getZipCode().equals(directMailing.getZipCode()))
						.collect(Collectors.toList());

				if (elegileStudent != null && elegileStudent.size() > 0) {
					String documentStudente = elegileStudent.get(0).getDocument();
					Course course = courseService.retrieveCourseOfStudent(String.valueOf(documentStudente));
					if (course != null) {
						Stream<Double> score = course.getScore().values().stream()
								.filter(c -> Double.parseDouble(String.valueOf(c)) < Double.parseDouble("7.0"));
						if (score != null) {
							mailingsElegileStudents.add(directMailing);
						}
					}

				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return mailingsElegileStudents;
	}
}