package br.com.matera.sge.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;
import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.DirectMailingService;
import br.com.matera.sge.service.StudentService;
import br.com.matera.sge.util.StaticsUtils;

@Service
public class DirectMailingServiceImpl implements DirectMailingService {

	private StudentService studentService;

	private CourseService courseService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectMailingServiceImpl.class);

	@Autowired
	public DirectMailingServiceImpl(StudentService studentService, CourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
	}

	@Override
	public List<DirectMailing> retrieveElegibleStudents(List<DirectMailing> mailings) throws ServiceException {
		List<DirectMailing> mailingsElegileStudents = new ArrayList<>();
		for (final DirectMailing directMailing : mailings) {
			Student elegileStudent;
			elegileStudent = getStudent(directMailing);

			Course course = retriveCoursesOfStudent(elegileStudent);
			if (isNotApproved(course) && StaticsUtils.extractOnlyNumbers(course.getDocument()).equals(StaticsUtils.extractOnlyNumbers(elegileStudent.getDocument()))) {
				mailingsElegileStudents.add(directMailing);
			}
		}
		return mailingsElegileStudents;
	}

	private Student getStudent(DirectMailing directMailing) throws ServiceException {
		try {
			for (Student s : studentService.retrieveAllStudents()) {
				if (s.getName().equals(directMailing.getName()) && s.getAddress().equals(directMailing.getAddress())
						&& s.getZipCode().equals(directMailing.getZipCode())) {
					return s;
				}
			}
		} catch (Exception e) {
			final String message = "Error when try connection with Student";
			LOGGER.error("M=handle: {}", message, e);
			throw new ServiceException(message, e);
		}
		return null;
	}

	private Course retriveCoursesOfStudent(Student elegileStudent) throws ServiceException {
		Course course = null;
		if (elegileStudent != null) {
			String documentStudente = elegileStudent.getDocument();
			try {
				course = courseService.retrieveCourseOfStudent(String.valueOf(documentStudente));
			} catch (Exception e) {
				final String message = "Error when try connection with Course";
				LOGGER.error("M=handle: {}", message, e);
				throw new ServiceException(message, e);
			}
		}
		return course;

	}

	private Boolean isNotApproved(Course course) {
		return course != null && orderScoreByCourse(course).isPresent() && orderScoreByCourse(course).get() < 7.0;
	}

	private Optional<Double> orderScoreByCourse(Course course) {
		return course.getScore().entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).findFirst()
				.map(Map.Entry::getValue);
	}
}