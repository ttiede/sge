package br.com.matera.sge.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.DirectMailing;

public interface DirectMailingService {

	List<DirectMailing> retrieveElegibleStudents(List<DirectMailing> mailings) throws ServiceException, JsonParseException, JsonMappingException, IOException;
}