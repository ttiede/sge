package br.com.matera.sge.service;

import br.com.matera.sge.exception.ServiceException;

public interface HttpHandlerService {
    String handle(final String endpoint) throws ServiceException;

}
