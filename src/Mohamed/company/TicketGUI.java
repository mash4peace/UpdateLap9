package Mohamed.company;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.LinkedList;

import static javax.swing.JOptionPane.*;

/**
 * Created by mash4 on 3/31/2017.
 */
public class TicketGUI extends JFrame {
    private JPanel rootPanel;
    private JTextField descriptextField1;
    private JTextField reportertextField2;
    private JTextField severitytextField3;
    private   JList reloadingSavedTickets;
    private JList openTicketlist2;
    private JList resolvedTicketslist3;
    private JButton addNewTicketButton;
    private JButton reloadPreviousTicketsButton;
    private JButton searchATicketButton;
    private JButton saveAndQuitButton;
    private JButton resolveATicketButton;
    private JComboBox severitycomboBox1;

    static DefaultListModel<TicketConstr> opeticketListModel;
    static  DefaultListModel<TicketConstr>resolvedTicketListModel;
    static LinkedList<TicketConstr> tiketQueue = new LinkedList<TicketConstr>();
    static LinkedList<ResolvedTicket> resTicketQue = new LinkedList<ResolvedTicket>();



    TicketGUI(){
        setTitle("TicketConstr Manager Programm !!!");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        severityLists();
        opeticketListModel = new DefaultListModel<>();
        openTicketlist2.setModel(opeticketListModel);
        resolvedTicketListModel = new DefaultListModel<>();
        resolvedTicketslist3.setModel(resolvedTicketListModel);

   


        addNewTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String des = descriptextField1.getText();
                String rep = reportertextField2.getText();
                if(des.length() == 0){
                    JOptionPane.showMessageDialog(TicketGUI.this, "Please type  description here ");
                    return;

                }
                if(rep.length() == 0){
                    showMessageDialog(TicketGUI.this, "Please, type reporter here ");
                    return;
                }

                String priority =(String ) severitycomboBox1.getSelectedItem();


                Date date = new Date();
                TicketConstr t = new TicketConstr(des, rep, priority, date);
                opeticketListModel.addElement(t);
                tiketQueue.add(t);

                descriptextField1.setText("");
                reportertextField2.setText("");


            }
        });
        resolveATicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(openTicketlist2.isSelectionEmpty()){
                    showMessageDialog(TicketGUI.this,"Lists are empty !!!" );

                }
                else {

                    TicketConstr tc = (TicketConstr) openTicketlist2.getSelectedValue();
                    String resolution = showInputDialog("Please enter a resolution ");
                    Date date = new Date();
                    String problem = tc.getDescription();
                    String rep = tc.getReporter();
                    String p = tc.getPriority();

                    ResolvedTicket reslT = new ResolvedTicket(problem, rep, p, resolution, date );
                    resolvedTicketListModel.addElement(reslT);
                    opeticketListModel.removeElement(tc);
                    resTicketQue.add(reslT);

                }
            }
        });
        saveAndQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int quit = showConfirmDialog(TicketGUI.this,
                        "Are you sure you want to quit ? ",
                        "Quit ", OK_CANCEL_OPTION);
                if(quit == OK_OPTION){
                    for(ResolvedTicket t : resTicketQue){
                        try{
                            BufferedWriter bf = new BufferedWriter
                                    (new FileWriter("allFiles.txt", true));
                            bf.append(String.valueOf(t)+ "\n");

                            bf.close();

                        }catch (IOException i){
                            System.out.println("There is an error reading from this file");
                            i.printStackTrace();
                        }

                    }
                    System.exit(0);

                }



            }
        });
        reloadPreviousTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel listModel = new DefaultListModel();
                try{
                    BufferedReader reader = new BufferedReader
                            (new FileReader("allFiles.txt"));
                    String line = reader.readLine();
                    while(line != null){
                        reloadingSavedTickets.setModel(listModel);
                        listModel.addElement(line);

                        line = reader.readLine();

                    }
                    reader.close();
                }catch (IOException io){
                    System.out.println("File reading input error!!!!");
                }
            }
        });


        searchATicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel listModel = new DefaultListModel();
               String problem = (showInputDialog(
                        TicketGUI.this, "Please, type " +
                               "" +
                               " the issuer here :  "));

                try {
                    BufferedReader reader = new BufferedReader
                            (new FileReader("allFiles.txt"));
                    String line = reader.readLine();
                    while (line != null) {
                        String item [] = new String[line.length()];
                        for (int i = 0; i < item.length; i++) {
                            for(ResolvedTicket t : resTicketQue){
                                if(problem.contains(t.getDescription())){
                                    System.out.println(t);
                                }
                            }

                        }



                        //System.out.println(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException io) {
                    System.out.println("File reading input error!!!!");
                }

            }



        });

        }

    private void severityLists() {

        for (int x = 1; x <= 5; x++){
            if(x == 1){
                severitycomboBox1.addItem(x + " severity ");

            }
            else{
                severitycomboBox1.addItem(x + " severities ");
            }

        }
    }


}
