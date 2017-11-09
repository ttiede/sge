package br.com.matera.sge.integ;

import br.com.matera.sge.Application;
import br.com.matera.sge.exception.ServiceException;
import br.com.matera.sge.model.Course;
import br.com.matera.sge.model.Student;
import br.com.matera.sge.service.CourseService;
import br.com.matera.sge.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class DirectMailingControllerTest {
	String exampleMalaDiretaJson = "[{\"nome\": \"Jose da Silva\",\"endereco\": \"Rua Silvio Santos, 55\",\"cep\": \"99999-999\",\"mensagem\": \"Texto referente a mensagem para o aluno\"},{\"nome\": \"Tiago Tiede\",\"endereco\": \"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\": \"18040-010\",\"mensagem\": \"Texto referente a mensagem para o aluno\"}]";
	@MockBean
	StudentService studentService;
	@MockBean
	CourseService courseService;

	private TestRestTemplate restTemplate;
	private SimpleDateFormat df;
	private String formatted;
	private String errorMsg;
	private String content;
	private ObjectMapper mapper;
	private Course mockCourse;
	private String contentCourse;

	@Before
	public void setData() {
		restTemplate = new TestRestTemplate();
		df = new SimpleDateFormat("yyyy-MM-dd");

		formatted = df.format(new Date());
		errorMsg = "{\"timestamp\":\"" + formatted
				+ "\",\"status\":500,\"error\":\"Internal Server Error\",\"exception\":\"br.com.matera.sge.exception.ServiceException\",\"message\":\"erro durante a comunicacao\",\"path\":\"/maladireta\"}";
		content = "[{\"documento\":\"123.123.123-21\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"}]";
		mapper = new ObjectMapper();

		mockCourse = new Course("12312312321", getScore());

		contentCourse = "{\"cpf\": \"123.123.123-21\",\"notas\": {\"disciplina_1\": 10,\"disciplina_2\": 8.4,\"disciplina_3\": 7.3,\"disciplina_4\": 5.4}}";

	}

	@LocalServerPort
	private int port;

	@Test
	public void sendForOneStudent() throws Exception {
		Mockito.when(courseService.retrieveCourseOfStudent(Mockito.anyString())).thenReturn(mockCourse);

		Mockito.when(studentService.retrieveAllStudents())
				.thenReturn((Arrays.asList(mapper.readValue(content, Student[].class))));

		String response = postMalaDireta();

		assertNotNull(response);
		assertEquals("{\"studentsAffected\":1}", response);
	}

	@Test
	public void dontSendForStudents() throws Exception {
		Mockito.when(courseService.retrieveCourseOfStudent(Mockito.anyString())).thenReturn(mockCourse);

		content = "[{\"documento\":\"225.123.123-21\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"}]";

		Mockito.when(studentService.retrieveAllStudents())
				.thenReturn((Arrays.asList(mapper.readValue(content, Student[].class))));

		String response = postMalaDireta();

		assertNotNull(response);
		assertEquals("{\"studentsAffected\":0}", response);
	}

	@Test
	public void serviceExceptionStudent() throws Exception {

		Mockito.when(courseService.retrieveCourseOfStudent(Mockito.anyString())).thenReturn(mockCourse);

		Mockito.when(studentService.retrieveAllStudents()).thenThrow(new ServiceException(content, null));

		String response = postMalaDireta();

		assertEquals(errorMsg, response);
	}

	@Test
	public void serviceExceptionCourse() throws Exception {
		Mockito.when(courseService.retrieveCourseOfStudent(Mockito.anyString()))
				.thenThrow(new ServiceException("Course error", null));

		Mockito.when(studentService.retrieveAllStudents())
				.thenReturn((Arrays.asList(mapper.readValue(content, Student[].class))));

		String response = postMalaDireta();

		assertEquals(errorMsg, response);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String postMalaDireta() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(exampleMalaDiretaJson, headers);
		return restTemplate.postForObject(createURLWithPort("/maladireta"), entity, String.class);

	}

	private HashMap<String, Double> getScore() {
		HashMap<String, Double> score = null;

		score = new HashMap<>();
		score.put("disciplina_1", 10.0);
		score.put("disciplina_2", 6.0);
		score.put("disciplina_3", 7.1);
		score.put("disciplina_4", 5.0);

		return score;
	}
}
