package br.com.matera.sge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matera.sge.model.DirectMailing;

@Service
public class DirectMailingServiceImpl implements DirectMailingService {

	@Autowired
	private StudentService studentService;

	@Override
	public String retrieveElegibleStudents(List<DirectMailing> mailings) {
		return "" + studentService.retrieveAllStudents();
	}

}