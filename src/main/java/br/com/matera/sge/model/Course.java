package br.com.matera.sge.model;

import java.util.HashMap;

public class Course {
	private String id;
	private String cpf;
	private HashMap<String, Double> score;

	public Course() {

	}

	public Course(String id, String cpf, HashMap<String, Double> score) {
		super();
		this.id = id;
		this.cpf= cpf;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public HashMap<String, Double> getScore() {
		return score;
	}

	@Override
	public String toString() {
		return String.format(
				"Course [id=%s, cpf=%s, score=%s]", id, cpf, score);
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