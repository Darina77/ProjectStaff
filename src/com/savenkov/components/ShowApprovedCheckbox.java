package com.savenkov.components;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.function.Consumer;

public class ShowApprovedCheckbox extends JPanel {
    public ShowApprovedCheckbox(Consumer<Boolean> onChange) {
        JCheckBox checkBox = new JCheckBox("Show not finished yet transactions");

        checkBox.setBounds(0, 0, 100, 50);

        checkBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
                boolean isChecked = checkBox.getModel().isSelected();

                onChange.accept(isChecked);
            }
        });

        this.add(checkBox);
    }
}
