import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import java.awt.Label;
import java.awt.Panel;

public class ToDoList extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldDate;
    private JTextField textFieldDuration;
    private ButtonGroup bg = new ButtonGroup();
    private JLabel totalLabel= new JLabel();
    private DefaultTableModel model;
    static FileWriter fileWriter;
    private JTable table;
    private JTextField textFieldTask;

    Connection conn = null;
    Statement stat;
    PreparedStatement pst;
    ResultSet rs;




// Result Set Table Model

    private void resultSetToTableModel(ResultSet rs) {
        // TODO Auto-generated method stub
        model.setRowCount(0); //reset or clear table model
        int count=0;
        try {
            while(rs.next()) { // add each in the result net rs to the JTable
                Object[] row= { rs.getString("Year"), rs.getString("Month"),
                        rs.getString("Date"), rs.getString("Day"),
                        rs.getString("Task"), rs.getString("Duration"),rs.getString("Status"),
                        rs.getString("Importance")};
                model.addRow(row);
                count++; // increment number of student records copied to the Jtable
            }
            totalLabel.setText("Total Tasks: " +count );
        }

        catch (Exception err) {
            JOptionPane.showMessageDialog(null, "Result Exception " +err);

        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ToDoList frame = new ToDoList();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    private Connection dbConnect() {
        // TODO Auto-generated method stub
        try {
            Class.forName("org.sqlite.JDBC");   // Load Java JDBC Driver
            // Connect to SQLite
            conn= DriverManager.getConnection("JDBC:sqlite:C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\ToDoList.sqlite");
            return conn;
        }
        catch (Exception err){
            JOptionPane.showMessageDialog(null, "Connection Failed " +err);
            return null;
        }


    }


    public ToDoList() {
        setTitle("To Do Lists");
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-calendar-40.png"));

        conn = dbConnect();  // connect to SQLite Database
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 962, 591);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-drink-time-100.png"));
        lblNewLabel_3.setBounds(775, 451, 111, 93);
        contentPane.add(lblNewLabel_3);

        JPanel panel = new JPanel();
        panel.setForeground(SystemColor.scrollbar);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "To Do List", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.desktop));
        panel.setBounds(656, 92, 268, 327);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Year: ");
        lblNewLabel.setForeground(SystemColor.desktop);
        lblNewLabel.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblNewLabel.setBounds(20, 38, 61, 22);
        panel.add(lblNewLabel);

        JLabel lblMonth = new JLabel("Month:");
        lblMonth.setForeground(SystemColor.desktop);
        lblMonth.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblMonth.setBounds(20, 70, 61, 22);
        panel.add(lblMonth);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setForeground(SystemColor.desktop);
        lblDate.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblDate.setBounds(20, 103, 61, 22);
        panel.add(lblDate);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setForeground(SystemColor.desktop);
        lblDay.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblDay.setBounds(20, 135, 61, 22);
        panel.add(lblDay);

        JLabel lblDuration = new JLabel("Duration:");
        lblDuration.setForeground(SystemColor.desktop);
        lblDuration.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblDuration.setBounds(20, 168, 83, 22);
        panel.add(lblDuration);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setForeground(SystemColor.desktop);
        lblStatus.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblStatus.setBounds(20, 201, 52, 22);
        panel.add(lblStatus);

        JLabel lblImportant = new JLabel("Important");
        lblImportant.setForeground(SystemColor.desktop);
        lblImportant.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblImportant.setBounds(20, 234, 83, 22);
        panel.add(lblImportant);

        //ComboBox Year
        JComboBox comboBoxYear = new JComboBox();
        comboBoxYear.setBackground(UIManager.getColor("ComboBox.buttonBackground"));
        comboBoxYear.setFont(new Font("Calibri", Font.BOLD, 13));
        comboBoxYear.setBounds(74, 39, 82, 21);
        panel.add(comboBoxYear);
        comboBoxYear.addItem("2022");
        for (int i=10; i<99 ; i++)
            comboBoxYear.addItem("20"+i);

        JComboBox comboBoxMonth = new JComboBox();
        comboBoxMonth.setBounds(73, 70, 83, 22);
        panel.add(comboBoxMonth);
        //	ComboBox Month
        comboBoxMonth.addItem("January"); comboBoxMonth.addItem("February");
        comboBoxMonth.addItem("March"); comboBoxMonth.addItem("April");
        comboBoxMonth.addItem("May"); comboBoxMonth.addItem("June");
        comboBoxMonth.addItem("July"); comboBoxMonth.addItem("August");
        comboBoxMonth.addItem("September"); comboBoxMonth.addItem("October");
        comboBoxMonth.addItem("November"); comboBoxMonth.addItem("December");
        //
        textFieldDate = new JTextField();
        textFieldDate.setBounds(74, 104, 83, 20);
        panel.add(textFieldDate);
        textFieldDate.setColumns(10);

        JComboBox comboBoxDay = new JComboBox();
        comboBoxDay.setBounds(74, 135, 83, 22);
        panel.add(comboBoxDay);
        // COMBOBOX DAY
        comboBoxDay.addItem("Monday"); comboBoxDay.addItem("Tuesday"); comboBoxDay.addItem("Wednesday");
        comboBoxDay.addItem("Thursday"); comboBoxDay.addItem("Friday"); comboBoxDay.addItem("Saturday");
        comboBoxDay.addItem("Sunday");
        //

        textFieldDuration = new JTextField();
        textFieldDuration.setBounds(98, 170, 69, 20);
        panel.add(textFieldDuration);
        textFieldDuration.setColumns(10);

        JRadioButton rdbtnNewRadioButtonDone = new JRadioButton("Done");
        rdbtnNewRadioButtonDone.setForeground(SystemColor.desktop);
        rdbtnNewRadioButtonDone.setBackground(SystemColor.inactiveCaption);
        rdbtnNewRadioButtonDone.setFont(new Font("Tahoma", Font.PLAIN, 10));
        rdbtnNewRadioButtonDone.setBounds(74, 201, 52, 23);
        panel.add(rdbtnNewRadioButtonDone);

        JRadioButton rdbtnNewRadioButtonPending = new JRadioButton("Undone");
        rdbtnNewRadioButtonPending.setForeground(SystemColor.desktop);
        rdbtnNewRadioButtonPending.setBackground(SystemColor.inactiveCaption);
        rdbtnNewRadioButtonPending.setFont(new Font("Tahoma", Font.PLAIN, 10));
        rdbtnNewRadioButtonPending.setBounds(126, 201, 69, 23);
        panel.add(rdbtnNewRadioButtonPending);

        // BUTTONGROUP
        bg.add(rdbtnNewRadioButtonPending);
        bg.add(rdbtnNewRadioButtonDone);
        //

        JCheckBox chckbxNewCheckBoxImportance = new JCheckBox("");
        chckbxNewCheckBoxImportance.setBackground(SystemColor.inactiveCaption);
        chckbxNewCheckBoxImportance.setBounds(105, 234, 45, 23);
        panel.add(chckbxNewCheckBoxImportance);

        JButton btnNewButton_1Year = new JButton("");
        btnNewButton_1Year.setForeground(SystemColor.scrollbar);
        btnNewButton_1Year.setEnabled(true);
        btnNewButton_1Year.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1Year.addActionListener(new ActionListener() {
                                                 public void actionPerformed(ActionEvent e) {
                                                     String year= (String) comboBoxYear.getSelectedItem();
/*
	# Year Data #
*/
                                                     try {
                                                         String query= "select * from ToDoList where Year="+year; // select all data from database
                                                         pst= conn.prepareStatement(query);

                                                         //execute SQL command
                                                         rs= pst.executeQuery();
                                                         // convert result to table model
                                                         resultSetToTableModel(rs);
                                                     }
                                                     catch (Exception err) {
                                                         JOptionPane.showMessageDialog(null, " Error 1" +err);
                                                     }


                                                 }
                                             }
        );
        btnNewButton_1Year.setBounds(168, 38, 27, 22);
        panel.add(btnNewButton_1Year);

        JButton btnNewButton_1_1Month = new JButton("");
        btnNewButton_1_1Month.setForeground(SystemColor.scrollbar);
        btnNewButton_1_1Month.addMouseListener(new MouseAdapter() {
                                                   @Override
                                                   public void mouseClicked(MouseEvent e) {
                                                       String month= (String) comboBoxMonth.getSelectedItem();
/*
		# Month Data #
*/
                                                       try {
                                                           String query= "select * from ToDoList where Month='"+month+"'"; // select all data from database
                                                           pst= conn.prepareStatement(query);

                                                           //execute SQL command
                                                           rs= pst.executeQuery();
                                                           // convert result to table model
                                                           resultSetToTableModel(rs);
                                                       }
                                                       catch (Exception err) {
                                                           JOptionPane.showMessageDialog(null, " Error 1" +err);
                                                       }


                                                   }
                                               }
        );

        btnNewButton_1_1Month.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_1Month.setEnabled(true);
        btnNewButton_1_1Month.setBounds(168, 70, 27, 22);
        panel.add(btnNewButton_1_1Month);

        JButton btnNewButton_1_2Date = new JButton("");
        btnNewButton_1_2Date.setForeground(SystemColor.scrollbar);
        btnNewButton_1_2Date.addActionListener(new ActionListener() {
                                                   public void actionPerformed(ActionEvent e) {
                                                       String date= textFieldDate.getText();
/*
	# Date Data #
*/
                                                       try {
                                                           String query= "select * from ToDoList where Date="+date; // select all data from database
                                                           pst= conn.prepareStatement(query);

                                                           //execute SQL command
                                                           rs= pst.executeQuery();
                                                           // convert result to table model
                                                           resultSetToTableModel(rs);
                                                       }
                                                       catch (Exception err) {
                                                           JOptionPane.showMessageDialog(null, " Error 1" +err);
                                                       }


                                                   }
                                               }
        );
        btnNewButton_1_2Date.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_2Date.setEnabled(true);
        btnNewButton_1_2Date.setBounds(168, 104, 27, 22);
        panel.add(btnNewButton_1_2Date);

        JButton btnNewButton_1_3Day = new JButton("");
        btnNewButton_1_3Day.setForeground(SystemColor.scrollbar);
        btnNewButton_1_3Day.addActionListener(new ActionListener() {
                                                  public void actionPerformed(ActionEvent e) {
                                                      String day= (String) comboBoxDay.getSelectedItem();
/*
	# Day Data #
*/

                                                      try {
                                                          String query= "select * from ToDoList where Day='"+day+"'"; // select all data from database
                                                          pst= conn.prepareStatement(query);

                                                          //execute SQL command
                                                          rs= pst.executeQuery();
                                                          // convert result to table model
                                                          resultSetToTableModel(rs);
                                                      }
                                                      catch (Exception err) {
                                                          JOptionPane.showMessageDialog(null, " Error 1" +err);
                                                      }


                                                  }

                                              }
        );
        btnNewButton_1_3Day.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_3Day.setEnabled(true);
        btnNewButton_1_3Day.setBounds(168, 136, 27, 22);
        panel.add(btnNewButton_1_3Day);

        JButton btnNewButton_1_4Duration = new JButton("");
        btnNewButton_1_4Duration.setForeground(SystemColor.scrollbar);
        btnNewButton_1_4Duration.addActionListener(new ActionListener() {
                                                       public void actionPerformed(ActionEvent e) {
                                                           String duration= textFieldDuration.getText();
/*
	# Duration Data #
*/

                                                           try {
                                                               String query= "select * from ToDoList where Duration='"+duration+"'"; // select all data from database
                                                               pst= conn.prepareStatement(query);

                                                               //execute SQL command
                                                               rs= pst.executeQuery();
                                                               // convert result to table model
                                                               resultSetToTableModel(rs);
                                                           }
                                                           catch (Exception err) {
                                                               JOptionPane.showMessageDialog(null, " Error 1" +err);
                                                           }


                                                       }
                                                   }
        );
        btnNewButton_1_4Duration.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_4Duration.setEnabled(true);
        btnNewButton_1_4Duration.setBounds(207, 168, 27, 22);
        panel.add(btnNewButton_1_4Duration);

        JButton btnNewButton_1_5Status = new JButton("");
        btnNewButton_1_5Status.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String status= status= (rdbtnNewRadioButtonDone.isSelected())? rdbtnNewRadioButtonDone.getLabel():rdbtnNewRadioButtonPending.getLabel();
/*
	# Status Data #
*/

                try {
                    String query= "select * from ToDoList where Status='"+status+"'"; // select all data from database
                    pst= conn.prepareStatement(query);

                    //execute SQL command
                    rs= pst.executeQuery();
                    // convert result to table model
                    resultSetToTableModel(rs);
                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, " Error 1" +err);
                }


            }
        });
        btnNewButton_1_5Status.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_5Status.setEnabled(true);
        btnNewButton_1_5Status.setBounds(207, 201, 27, 22);
        panel.add(btnNewButton_1_5Status);

        JButton btnNewButton_1_6Importance = new JButton("");
        btnNewButton_1_6Importance.setForeground(SystemColor.scrollbar);
        btnNewButton_1_6Importance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String important= chckbxNewCheckBoxImportance.isSelected()? "Important": "Not Important";

/*
	# Importance Data #
*/

                try {
                    String query= "select * from ToDoList where Importance='"+important+"'"; // select all data from database
                    pst= conn.prepareStatement(query);

                    //execute SQL command
                    rs= pst.executeQuery();
                    // convert result to table model
                    resultSetToTableModel(rs);
                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, " Error 1" +err);
                }


            }
        });
        btnNewButton_1_6Importance.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Start-Menu-Search-icon (1).png"));
        btnNewButton_1_6Importance.setEnabled(true);
        btnNewButton_1_6Importance.setBounds(207, 234, 27, 22);
        panel.add(btnNewButton_1_6Importance);

        JLabel lblTask = new JLabel("Task:");
        lblTask.setForeground(SystemColor.desktop);
        lblTask.setFont(new Font("Segoe Script", Font.PLAIN, 13));
        lblTask.setBounds(20, 284, 45, 22);
        panel.add(lblTask);

        textFieldTask = new JTextField();
        textFieldTask.setBounds(60, 286, 97, 19);
        panel.add(textFieldTask);
        textFieldTask.setColumns(10);

        JButton btnNewButton_1Edit = new JButton("");
        btnNewButton_1Edit.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-edit-20.png"));
        btnNewButton_1Edit.setBounds(168, 285, 27, 21);
        panel.add(btnNewButton_1Edit);

        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-remove-20.png"));
        btnNewButton_1.setBounds(203, 284, 27, 22);
        panel.add(btnNewButton_1);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task= textFieldTask.getText();
/*
	# Delete Data #
*/
                // create a statement through the connection
                try {
                    stat= conn.createStatement();
                    // execute the Statement which is to delete a certain task
                    stat.executeUpdate("delete from ToDoList where Task='"+task+"'");

                    // Prepare SQL command
                    String query= "select * from ToDoList"; // select all data from database
                    pst= conn.prepareStatement(query);

                    //execute SQL command
                    rs= pst.executeQuery();
                    // convert result to table model
                    resultSetToTableModel(rs);
                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, " Error 1" +err);
                }

            }
        });
        btnNewButton_1Edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String important= chckbxNewCheckBoxImportance.isSelected()? "Important": "Not Important";
                String status= status= (rdbtnNewRadioButtonDone.isSelected())? rdbtnNewRadioButtonDone.getLabel():rdbtnNewRadioButtonPending.getLabel();
                String day= (String) comboBoxDay.getSelectedItem();
                String task= textFieldTask.getText();


/*
	# Edit Data #
*/
                try {
                    stat= conn.createStatement();
                    // execute the Statement which is to delete a certain task
                    stat.executeUpdate("update ToDoList set Status= '"+status+"' where Task='"+task+"'");

                    // Prepare SQL command
                    String query= "select * from ToDoList"; // select all data from database
                    pst= conn.prepareStatement(query);

                    //execute SQL command
                    rs= pst.executeQuery();
                    // convert result to table model
                    resultSetToTableModel(rs);
                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, " Error 1" +err);
                }

            }

        });

/*
		# Load Data #
*/

        JButton btnNewButton = new JButton("Load Data");
        btnNewButton.setBackground(UIManager.getColor("Button.background"));
        btnNewButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 10));
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-load-20.png"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Prepare SQL command
                    String query= "select * from ToDoList"; // select all data from database
                    pst= conn.prepareStatement(query);

                    //execute SQL command
                    rs= pst.executeQuery();
                    // convert result to table model
                    resultSetToTableModel(rs);

                }
                catch (Exception err) {
                    JOptionPane.showMessageDialog(null, " Error 1" +err);
                }


            }
        });

/*
	# JTable Area #
*/

        btnNewButton.setBounds(656, 431, 111, 23);
        contentPane.add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.RAISED, SystemColor.desktop, SystemColor.desktop, SystemColor.desktop, SystemColor.desktop));
        scrollPane.setBounds(26, 73, 603, 441);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Year", "Month", "Date", "Day", "Task", "Duration", "Status", "Importance"
                }
        ));

        table.addMouseListener(new MouseAdapter() {
            /*
                 select row from Jtable 			@Override
            */
            public void mouseClicked(MouseEvent e) {

                int selectedRowIndex= table.getSelectedRow();
                textFieldTask.setText(model.getValueAt(selectedRowIndex,4).toString());
            }
        });

        JButton btnNewButton_2 = new JButton("Add Task");
        btnNewButton_2.setForeground(Color.BLACK);
        btnNewButton_2.setBackground(UIManager.getColor("Button.background"));
        btnNewButton_2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 10));
        btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-add-20.png"));
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        btnNewButton_2.setBounds(656, 464, 111, 21);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Print");
        btnNewButton_3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 10));
        btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-print-20.png"));
        btnNewButton_3.addMouseListener(new MouseAdapter() {

            /* Print CSV file */
            @Override

            public void mouseClicked(MouseEvent e) {

                //Write ToDoList Record in CSV file
                try {
                    fileWriter= new FileWriter("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\ToDoList.csv", false);
                    BufferedWriter wrt= new BufferedWriter(fileWriter);
                    for (int r= 0; r < table.getRowCount(); r++) {
                        wrt.write("\n");
                        for (int c= 0; c < table.getColumnCount(); c++) {
                            if (c!=0) wrt.write(",");
                            wrt.write(table.getValueAt(r, c).toString());
                        }
                    }
                    wrt.newLine();
                    JOptionPane.showMessageDialog(null, " Successfully Saved to CSV File ");
                    wrt.close();
                    fileWriter.close();
                }
                catch (Exception err){
                    JOptionPane.showMessageDialog(null, " Print Failed " +err);
                }
            }
        });
        //Totallabel
        totalLabel = new JLabel("");
        totalLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        totalLabel.setBounds(32, 53, 118, 23);
        contentPane.add(totalLabel);
        btnNewButton_3.setBounds(656, 493, 111, 21);
        contentPane.add(btnNewButton_3);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-list-100.png"));
        lblNewLabel_1.setBounds(572, 10, 151, 112);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-notes-100 (1).png"));
        lblNewLabel_2.setBounds(816, 429, 108, 108);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\Webp.net-resizeimage.jpg"));
        lblNewLabel_5.setBounds(26, 53, 124, 23);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-kitty-70.png"));
        lblNewLabel_4.setBounds(43, 10, 70, 66);
        contentPane.add(lblNewLabel_4);


// adjust the column
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(15);
        table.getColumnModel().getColumn(3).setPreferredWidth(30);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(25);

// Initialized Model
        model= (DefaultTableModel) table.getModel();
    }
}
