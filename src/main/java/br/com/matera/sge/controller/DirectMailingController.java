package br.com.matera.sge.controller;

import java.util.List;

import br.com.matera.sge.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.service.DirectMailingService;

@RestController
public class DirectMailingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectMailingController.class);

	private final DirectMailingService directMailingService;

	@Autowired
	public  DirectMailingController(DirectMailingService directMailingService) {
		this.directMailingService = directMailingService;
	}

	@PostMapping("/maladireta")
	public Result registerStudentForMailing(@RequestBody List<DirectMailing> mailings) throws ServiceException {
		try {
			Result result = new Result();
			result.setStudentsAffected(directMailingService.retrieveElegibleStudents(mailings).size());
			return result;
		} catch (Exception e) {
            final String message = "erro durante a comunicacao";
            LOGGER.error("M=handle: {}", message, e);
            throw new ServiceException(message, e);
		}
	}
}
