package br.com.matera.sge.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.matera.sge.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class DirectMailingControllerTest {
	String exampleMalaDiretaJson = "[{\"nome\": \"Jose da Silva\",\"endereco\": \"Rua Silvio Santos, 55\",\"cep\": \"99999-999\",\"mensagem\": \"Texto referente a mensagem para o aluno\"},{\"nome\": \"Tiago Tiede\",\"endereco\": \"Rua Maestro Zeferino Santa, 132, apto 6\",\"cep\": \"18040-010\",\"mensagem\": \"Texto referente a mensagem para o aluno\"}]";

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void addCourse() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(exampleMalaDiretaJson, headers);
		String response = restTemplate.postForObject(createURLWithPort("/maladireta"), entity, String.class);

		System.out.println(response);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
