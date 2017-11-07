package br.com.matera.sge.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matera.sge.Application;
import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class DirectMailingControllerTest {
	String exampleMalaDiretaJson = "[{\"nome\": \"Jose da Silva\",\"endereco\": \"Rua Silvio Santos, 55\",\"cep\": \"99999-999\",\"mensagem\": \"Texto referente a mensagem para o aluno\"},{\"nome\": \"Tiago Tiede\",\"endereco\": \"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\": \"18040-010\",\"mensagem\": \"Texto referente a mensagem para o aluno\"}]";

	@LocalServerPort
	private int port;

	@MockBean
	StudentService studentService;

	@MockBean
	CourseService courseService;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void addCourse() throws ServiceException, JsonParseException, JsonMappingException, IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);


		HashMap<String, Double> score =null;

		score = new HashMap<>();
		score.put("disciplina_1", 10.0);
		score.put("disciplina_2", 6.0);
		score.put("disciplina_3", 7.1);
		score.put("disciplina_4", 5.0);
	
				
		Course mockCourse = new Course("12312312321", score);
		
		Mockito.when(
				courseService.retrieveCourseOfStudent(Mockito.anyString())).thenReturn(mockCourse);


		String content = "[{\"documento\":\"123.123.123-21\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"}]";
		ObjectMapper mapper = new ObjectMapper();

		Mockito.when(
				studentService.retrieveAllStudents()).thenReturn((Arrays.asList(mapper.readValue(content, Student[].class))));

		HttpEntity<String> entity = new HttpEntity<String>(exampleMalaDiretaJson, headers);
		String response = restTemplate.postForObject(createURLWithPort("/maladireta"), entity, String.class);

		System.out.println(response);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
