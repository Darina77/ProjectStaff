package com.savenkov.components;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DatePeriodPicker extends JPanel {

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

    public static class DatePeriod {
        public Date start, end;

        public DatePeriod() {
            try {
                start = (Date)formatter.stringToValue("2010-01-01");
                end = (Date)formatter.stringToValue("2020-01-01");
            } catch(ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private DatePeriod datePeriod = new DatePeriod();

    public DatePeriodPicker(Consumer<DatePeriod> onChange) {
        renderDatePicker(100, 100, datePeriod.start, (date, datePicker) -> {
            if(date.after(datePeriod.end)) {
                date = datePeriod.end;
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                datePicker.getModel().setDate(year, month, day);
            }
            datePeriod.start = date;
            onChange.accept(datePeriod);
        });
        renderDatePicker(350, 100, datePeriod.end, (date, datePicker) -> {
            if(date.before(datePeriod.start)) {
                date = datePeriod.start;
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                datePicker.getModel().setDate(year, month, day);
            }
            datePeriod.end = date;
            onChange.accept(datePeriod);
        });
    }

    private JDatePickerImpl renderDatePicker(int x, int y, Date initialDate, BiConsumer<Date, JDatePickerImpl> onChange) {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.setProperty("text.today", "Today");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, formatter);

        datePicker.setBounds(x, y, 150, 100);

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
