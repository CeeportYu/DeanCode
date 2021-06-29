package model;

import java.util.Set;

public class Person {
	private int id;
	private String name;
	
	private String[] mail;
	private Set<String> chName;
	
	
	
	
	
	
	
	
	
	
	
	public Set<String> getChName() {
		return chName;
	}
	public void setChName(Set<String> chName) {
		this.chName = chName;
	}
	public String[] getMail() {
		return mail;
	}
	public void setMail(String[] mail) {
		this.mail = mail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
