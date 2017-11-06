package br.com.matera.sge.service;

import java.util.List;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.DirectMailing;

public interface DirectMailingService {

	List<DirectMailing> retrieveElegibleStudents(List<DirectMailing> mailings) throws ServiceException;
}