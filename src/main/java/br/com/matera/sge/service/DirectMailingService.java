package br.com.matera.sge.service;

import java.util.List;

import br.com.matera.sge.model.DirectMailing;

public interface DirectMailingService {

	String retrieveElegibleStudents(List<DirectMailing> mailings);
}