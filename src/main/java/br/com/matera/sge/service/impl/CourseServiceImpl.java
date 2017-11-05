package br.com.matera.sge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.matera.sge.model.Course;
import br.com.matera.sge.service.CourseService;

@Service
public class CourseServiceImpl  implements CourseService{

	private static List<Course > courses = new ArrayList<>();

	static {
		// Initialize Data
		Course jose = new Course(null, null, null);

		courses.add(jose);

	}

	public Course retrieveCourseStudent(String studentDocument) {
		for (Course course : courses) {
			if (course.getDocument().equals(studentDocument)) {
				return course;
			}
		}
		return null;
	}
}