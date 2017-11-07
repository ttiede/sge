package br.com.matera.sge.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matera.sge.model.Course;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.DirectMailingService;
import br.com.matera.sge.service.StudentService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = DirectMailingController.class, secure = false)
public class DirectMailingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@MockBean
	private StudentService studentService;

	@MockBean
	private DirectMailingService directMailingService;

	Course mockCourse = new Course("99999999999", null);
	
	String exampleMalaDiretaJson = "[{\"nome\": \"Jose da Silva\",\"endereco\": \"Rua Silvio Santos, 55\",\"cep\": \"99999-999\",\"mensagem\": \"Texto referente a mensagem para o aluno\"},{\"nome\": \"Tiago Tiede\",\"endereco\": \"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\": \"18040-010\",\"mensagem\": \"Texto referente a mensagem para o aluno\"}]";
	String content = "[{\"documento\":\"999.999.999-99\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"},{\"documento\":\"888.888.888-88\",\"nome\":\"Jose da Silva Jr\",\"endereco\":\"Rua Silvio Santos, 54\",\"cep\":\"99999-888\"},{\"documento\":\"325.353.222-38\",\"nome\":\"Tiago Tiede\",\"endereco\":\"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\":\"18040-010\"}]";

	
	@Test
	public void malaDireta() throws Exception {
		HashMap<String, Double> score = new HashMap<String, Double>();
		
		score.put("disciplina_1", 10.0);
		score.put("disciplina_2", 7.0);
		score.put("disciplina_3", 5.0);
		score.put("disciplina_4", 6.0);
		score.put("disciplina_5", 2.0);

		Course mockCourse = new Course("99999999999", score);

		ObjectMapper mapper = new ObjectMapper();

		List<Student> students = new ArrayList<>();
		students.addAll(Arrays.asList(mapper.readValue(content, Student[].class)));

		
		Mockito.when(
				studentService.retrieveAllStudents()).thenReturn(students);

		Mockito.when(
				courseService.retrieveCourseOfStudent(Mockito.anyString())).thenReturn(mockCourse);

		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/maladireta")
				.accept(MediaType.APPLICATION_JSON).content(exampleMalaDiretaJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	}

}
