package br.com.matera.sge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.HttpHandlerService;
import br.com.matera.sge.service.StudentService;

@Service
public class StudentServiceImpl  implements StudentService{

	private static List<Student> students = new ArrayList<>();
	
	@Autowired
    private HttpHandlerService httpHandlerService;
	
	public List<Student> retrieveAllStudents() {
		if (this.students.size() == 0) {
		    //this is a detour
            this.fillStudent();
        }
		return students;
	}

    private void fillStudent() {
        final String url = "url para o servico de estudantes";
        try {
            final String content = httpHandlerService.handle(url);
            //fazer o parse do conteudo usando jackson 
        } catch (ServiceException e) {
            //tratar o erro
            e.printStackTrace();
        }
    }
}