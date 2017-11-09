package br.com.matera.sge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
	@JsonProperty("nome")
	private String name;
	@JsonProperty("documento")
	private String document;
	@JsonProperty("endereco")
	private String address;
	@JsonProperty("cep")
	private String zipCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
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
}