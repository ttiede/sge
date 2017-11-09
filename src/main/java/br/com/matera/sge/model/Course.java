package br.com.matera.sge.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
	@JsonProperty("cpf")
	private String document;
	@JsonProperty("notas")
	private HashMap<String, Double> score = new HashMap<>();

	public Course() {

	}

	public Course(String cpf, HashMap<String, Double> score) {
		super();
		this.document= cpf;
		this.score = score;
	}
	
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setScore(HashMap<String, Double> score) {
		this.score = score;
	}

	public HashMap<String, Double> getScore() {
		return score;
	}
}