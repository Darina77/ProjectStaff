package com.rozhko.Models;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

public class MyComboBoxModel extends DefaultComboBoxModel<Item> {
	public MyComboBoxModel() {
		super();
	}

	public MyComboBoxModel(Vector<Item> items) {
		super(items);
	}
	

	@Override
	public Item getSelectedItem() {
		Item selectedJob = (Item) super.getSelectedItem();

		return selectedJob;
	}
}