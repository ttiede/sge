package br.com.matera.sge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.service.DirectMailingService;
import br.com.matera.sge.service.StudentService;

@Service
public class DirectMailingServiceImpl implements DirectMailingService {

	@Autowired
	private StudentService studentService;

	@Override
	public String retrieveElegibleStudents(List<DirectMailing> mailings) {
		return "" + studentService.retrieveAllStudents();
	}

}