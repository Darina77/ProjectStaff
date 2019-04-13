package com.savenkov;

import com.savenkov.dao.Dao;
import com.savenkov.models.Transaction;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CreateTransactionForm extends JFrame {

    Date selectedDate;
    double selectedIncome = 0, selectedExpenses = 0;
    String description = "";
    boolean isConfirmed = false;

    public CreateTransactionForm(Consumer<Void> onClose) {
        this.setSize(400, 500);

        try {
            selectedDate = (Date) formatter.stringToValue("2010-10-10");
        } catch(Exception e) {
            e.printStackTrace();
        }

//        renderDatePicker(50, 50, selectedDate, (newDate, x) -> {
//            selectedDate = newDate;
//        });

        JLabel header = new JLabel("Create transaction");
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBounds(100, 0, 200, 30);
        this.add(header);

        JLabel incomeLabel = new JLabel("Income");
        incomeLabel.setFont(new Font("Arial", 0, 14));
        incomeLabel.setBounds(50, 50, 100, 30);
        this.add(incomeLabel);

        SpinnerModel modelIncome = new SpinnerNumberModel(0, 0, 99999, 0.01);
        JSpinner spinnerIncome = new JSpinner(modelIncome);
        spinnerIncome.setBounds(150, 50, 200, 30);
        this.add(spinnerIncome);
        spinnerIncome.addChangeListener(e -> {
            selectedIncome = (double)spinnerIncome.getModel().getValue();
        });

        JLabel expensesLabel = new JLabel("Expenses");
        expensesLabel.setFont(new Font("Arial", 0, 14));
        expensesLabel.setBounds(50, 100, 100, 30);
        this.add(expensesLabel);

        SpinnerModel modelExpense = new SpinnerNumberModel(0, 0, 99999, 0.01);
        JSpinner spinnerExpense = new JSpinner(modelExpense);
        spinnerExpense.setBounds(150, 100, 200, 30);
        this.add(spinnerExpense);

        spinnerExpense.addChangeListener(e -> {
            selectedExpenses = (double)spinnerExpense.getModel().getValue();
        });

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setFont(new Font("Arial", 0, 14));
        descriptionLabel.setBounds(50, 150, 100, 30);
        this.add(descriptionLabel);

        JTextField descriptionTextarea = new JTextField(30);
        descriptionTextarea.setBounds(150, 150, 200, 20);
        descriptionTextarea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                description = descriptionTextarea.getText();
                System.out.println(description);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                description = descriptionTextarea.getText();
                System.out.println(description);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                description = descriptionTextarea.getText();
                System.out.println(description);
            }
        });
        this.add(descriptionTextarea);

        JButton buttonCreate = new JButton("Create transaction");

        buttonCreate.setBounds(50, 350, 150, 30);
        buttonCreate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Dao.createTransaction(new Transaction(
                        -1,
                        selectedIncome - selectedExpenses,
                        selectedDate, null, description), isConfirmed);

                onClose.accept(null);
                setVisible(false);
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.add(buttonCreate);

        JCheckBox checkBox = new JCheckBox("Transaction is confirmed");

        checkBox.setBounds(50, 320, 100, 50);

        checkBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED || e.getStateChange() == ItemEvent.DESELECTED) {
                boolean isChecked = checkBox.getModel().isSelected();

                isConfirmed = isChecked;
            }
        });

        this.add(checkBox);


    }

    private static JFormattedTextField.AbstractFormatter formatter = new JFormattedTextField.AbstractFormatter() {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    };

    private JDatePickerImpl renderDatePicker(int x, int y, Date initialDate, BiConsumer<Date, JDatePickerImpl> onChange) {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.setProperty("text.today", "Today");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, formatter);

        datePicker.setBounds(x, y, 150, 50);

        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker.getModel().setDate(year, month, day);

        model.setSelected(true);

        datePicker.addActionListener(e -> onChange.accept((Date) datePicker.getModel().getValue(), datePicker));


        this.add(datePicker);

        return datePicker;
    }
}
