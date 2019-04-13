package com.savenkov;

import com.savenkov.components.*;
import com.savenkov.dao.Dao;
import com.savenkov.dao.Db;
import com.savenkov.models.Saldo;
import com.savenkov.models.Transaction;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static JFrame frame = new JFrame();

    static Integer selectedProjectId = null;
    static boolean selectedShowAppoved = false;
    static DatePeriodPicker.DatePeriod selectedDatePeriod = new DatePeriodPicker.DatePeriod();

    static DataTable dataTable;

    static List<DataTableElement> data;
    static DataTableElement selectedElem;

    public static void main(String[] args) {

        Db.db = new Db(frame);

        JLabel header = new JLabel("Accounting");
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBounds(300, 0, 450, 50);
        frame.add(header);

        JLabel datePeriodLabel = new JLabel("Select date period");
        datePeriodLabel.setFont(new Font("Arial", 0, 14));
        datePeriodLabel.setBounds(30, 10, 450, 50);
        frame.add(datePeriodLabel);

        JPanel datePicker = new DatePeriodPicker(datePeriod -> {
            selectedDatePeriod = datePeriod;
            refreshTableData();
        });

        datePicker.setBounds(0, 40, 450, 50);

        frame.add(datePicker);

        ProjectSelector projectSelector = new ProjectSelector(projectId -> {
            selectedProjectId = projectId;
            refreshTableData();
        });

        projectSelector.setBounds(100, 40, 1000, 50);


        frame.add(projectSelector);

        ShowApprovedCheckbox showApprovedCheckbox = new ShowApprovedCheckbox(showApproved -> {
            selectedShowAppoved = showApproved;
            refreshTableData();
        });

        showApprovedCheckbox.setBounds(100, 85, 300, 40);

        frame.add(showApprovedCheckbox);


        dataTable = new DataTable();

        dataTable.setBounds(50, 130, 650, 200);

        frame.add(dataTable);

        refreshTableData();

        JButton button = new JButton("Confirm transaction");
        button.setEnabled(false);
        button.setBounds(100, 350, 220, 30);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                Dao.confirmTransaction((Transaction) selectedElem);
                refreshTableData();
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

        frame.add(button);

        dataTable.table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = dataTable.table.getSelectedRow();
                if(row != -1) {
                    DataTableElement elem = data.get(row);

                    if (elem instanceof Transaction) {
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }

                    selectedElem = elem;
                }
            }
        });


        frame.setSize(800,500);
        frame.setLayout(null);
        frame.setVisible(true);

        JButton buttonCreate = new JButton("Create transaction");
        buttonCreate.setBounds(450, 350, 220, 30);
        buttonCreate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                CreateTransactionForm createForm = new CreateTransactionForm(onClose -> {
                    refreshTableData();
                });
                createForm.setVisible(true);
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

        frame.add(buttonCreate);
    }

    private static void refreshTableData() {
        List<DataTableElement> elems = new ArrayList<DataTableElement>();

        List<Saldo> saldos = Dao.getSaldoByDatePeriodAndProject(selectedDatePeriod, selectedProjectId);

        elems.addAll(saldos);

        if(selectedShowAppoved) {
            List<Transaction> transactions = Dao.getNotFinishedTransactionsByDatePeriodAndProjectId(selectedDatePeriod, selectedProjectId);

            elems.addAll(transactions);
        }

        System.out.println(elems + " elems");

        dataTable.setData(elems);

        data = elems;
    }


}
