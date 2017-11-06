package br.com.matera.sge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

				Student elegileStudent = students.stream()
						.filter(s -> s.getName().equals(directMailing.getName())
								&& s.getAddress().equals(directMailing.getAddress())
								&& s.getZipCode().equals(directMailing.getZipCode()))
						.findFirst().orElse(null);

				Course course = retriveCoursesOfStudent(elegileStudent);
				if (isNotApproved(course)) {
					mailingsElegileStudents.add(directMailing);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return mailingsElegileStudents;
	}

	private Course retriveCoursesOfStudent(Student elegileStudent)
			throws JsonParseException, JsonMappingException, IOException {
		Course course = null;
		if (elegileStudent != null) {
			String documentStudente = elegileStudent.getDocument();
			course = courseService.retrieveCourseOfStudent(String.valueOf(documentStudente));
		}
		return course;

	}

	private Boolean isNotApproved(Course course) {
		if ((course != null) && orderScoreByCourse(course).get().compareTo(Double.parseDouble("7.0")) < 0) {
			return true;
		}
		return false;
	}

	private Optional<Double> orderScoreByCourse(Course course) {
		return course.getScore().entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).findFirst()
				.map(Map.Entry::getValue);
	}
}