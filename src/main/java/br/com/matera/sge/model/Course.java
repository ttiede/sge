package br.com.matera.sge.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
	private String id;
	@JsonProperty("cpf")
	private String document;
	@JsonProperty("notas")
	private HashMap<String, Double> score;

	public Course() {

	}

	public Course(String id, String document, HashMap<String, Double> score) {
		super();
		this.id = id;
		this.document= document;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return String.format(
				"Course [id=%s, cpf=%s, score=%s]", id, document, score);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}