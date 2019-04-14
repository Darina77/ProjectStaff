package com.rozhko.Models;

public class Data {
	private String name;
	private String project;
	private String stage;
	private String startDate;
	private String endDate;
	private String price;
	
	public Data(String name, String project, String stage, String startDate, String endDate, String price) {
		super();
		this.name = name;
		this.project = project;
		this.stage = stage;
		this.startDate = startDate.substring(0, 10);
		this.endDate = endDate.substring(0, 10);
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Data [name=" + name + ", project=" + project + ", stage=" + stage + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", price=" + price + "]";
	}
	
}
