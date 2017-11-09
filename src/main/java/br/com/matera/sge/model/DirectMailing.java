package br.com.matera.sge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectMailing {
	@JsonProperty("nome")
	private String name;
	@JsonProperty("endereco")
	private String address;
	@JsonProperty("cep")
	private String zipCode;
	@JsonProperty("mensagem")
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}