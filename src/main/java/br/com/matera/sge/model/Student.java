package br.com.matera.sge.model;

public class Student {
	private String name;
	private String document;
	private String address;
	private String zipCode;
	

	public Student(String document, String name, String address, String zipCode) {
		super();
		this.name = name;
		this.document= document;
		this.address= address;
		this.zipCode= zipCode;
	}

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
		this.document= document;
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

	@Override
	public String toString() {
		return String.format(
				"Student [name=%s, document=%s, address=%s, zipCode=%s]",
				name, document, address, zipCode);
	}
}