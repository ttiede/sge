package br.com.matera.sge.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
	@JsonProperty("cpf")
	private String document;
	@JsonProperty("notas")
	private HashMap<String, Double> score = new HashMap<>();

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
		return String.format("Course [cpf=%s, score=%s]", document, score);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((document == null) ? 0 : document.hashCode());
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
		if (document == null) {
			if (other.document!= null)
				return false;
		} else if (!document.equals(other.document))
			return false;
		return true;
	}

}