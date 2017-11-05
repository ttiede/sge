package br.com.matera.sge.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.matera.sge.model.DirectMailing;

@RestController
public class DirectMailingController {
	@PostMapping("/maladireta")
	public int registerStudentForMailing(@RequestBody List<DirectMailing> mailings) {
		return 0;
	}
}
