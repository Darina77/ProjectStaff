package com.rozhko.Models;

public class Item {
	private String id;
	private String value;
	
	public Item(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
