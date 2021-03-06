package br.com.matera.sge.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matera.sge.Application;
import br.com.matera.sge.model.DirectMailing;
import br.com.matera.sge.service.DirectMailingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class DirectMailingControllerTest {
	String exampleMalaDiretaJson = "[{\"nome\": \"Jose da Silva\",\"endereco\": \"Rua Silvio Santos, 55\",\"cep\": \"99999-999\",\"mensagem\": \"Texto referente a mensagem para o aluno\"},{\"nome\": \"Tiago Tiede\",\"endereco\": \"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\": \"18040-010\",\"mensagem\": \"Texto referente a mensagem para o aluno\"}]";
	@MockBean
	DirectMailingService directMailingService;

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate;
	private SimpleDateFormat df;
	private String formatted;
	private String errorMsg;
	private String content;
	private ObjectMapper mapper;
	private List<DirectMailing> mailings;
	private DirectMailing mockMailing;

	@Before
	public void setData() {
		restTemplate = new TestRestTemplate();
		df = new SimpleDateFormat("yyyy-MM-dd");

		formatted = df.format(new Date());
		errorMsg = "{\"timestamp\":\"" + formatted
				+ "\",\"status\":500,\"error\":\"Internal Server Error\",\"exception\":\"br.com.matera.sge.exception.ServiceException\",\"message\":\"erro durante a comunicacao\",\"path\":\"/maladireta\"}";
		content = "[{\"documento\":\"123.123.123-21\",\"nome\":\"Jose da Silva\",\"endereco\":\"Rua Silvio Santos, 55\",\"cep\":\"99999-999\"}]";
		mapper = new ObjectMapper();
		mailings = new ArrayList<>();

		mockMailing = new DirectMailing();
		mockMailing.setAddress("Rua Maestro Zeferino Santana 132 - apto 6");
		mockMailing.setMessage("Messagem para Aluno");
		mockMailing.setName("Tiago Tiede");
		mockMailing.setZipCode("18040-010");
	}

	@Test
	public void receiveOneMailing() throws Exception {

		mailings.add(mockMailing);

		Mockito.when(directMailingService.retrieveElegibleStudents(Mockito.anyList())).thenReturn(Arrays.asList(mockMailing));

		String response = postMalaDireta();

		assertNotNull(response);
		assertEquals("{\"studentsAffected\":1}", response);
	}

	@Test
	public void notreceiveMailing() throws Exception {

		Mockito.when(directMailingService.retrieveElegibleStudents(null)).thenReturn(Arrays.asList(mockMailing));

		String response = postMalaDireta();

		assertNotNull(response);
		assertEquals("{\"studentsAffected\":0}", response);
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
