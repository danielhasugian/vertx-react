package com.organization.project.model;

public class Employee {
	private int id;
	private String name;
	private String division;

	public Employee() {
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

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Employee(int id, String name, String division) {
		super();
		this.id = id;
		this.name = name;
		this.division = division;
	}

	@Override
	public String toString() {
		return "EmployeeModel [id=" + id + ", name=" + name + ", division=" + division + "]";
	}

}
