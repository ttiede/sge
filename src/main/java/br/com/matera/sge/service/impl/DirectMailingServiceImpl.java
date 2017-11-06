package br.com.matera.sge.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<DirectMailing> retrieveElegibleStudents(List<DirectMailing> mailings) {
		List<DirectMailing> mailingsElegileStudents = new ArrayList();
		try {
			List<Student> students = studentService.retrieveAllStudents();
			for (final DirectMailing directMailing : mailings) {
				for (final Student student : students) {
					if (isElegible(directMailing, student)) {
						Course courser = courseService.retrieveCourseOfStudent(student.getDocument());
						Iterator it = courser.getScore().values().iterator();
						while (it.hasNext()) {
							if (Double.parseDouble(String.valueOf(it.next())) < Double.parseDouble("7.0")) {
								mailingsElegileStudents.add(directMailing);
							}
						}
					}
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return mailingsElegileStudents;
	}

	private boolean isElegible(DirectMailing directMailing, Student student) {
		if (directMailing.getAddress().equals(student.getAddress()) && directMailing.getName().equals(student.getName())
				&& directMailing.getZipCode().equals(student.getZipCode())) {
			return true;
		}
		return false;
	}
}