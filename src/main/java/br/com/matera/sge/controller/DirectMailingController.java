package br.com.matera.sge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.service.DirectMailingService;

@RestController
public class DirectMailingController {

	@Autowired
	private DirectMailingService directMailingService;

	@PostMapping("/maladireta")
	public String registerStudentForMailing(@RequestBody List<DirectMailing> mailings) {
		try {
			return String.valueOf(directMailingService.retrieveElegibleStudents(mailings).size());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
