import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class UserInput {

	private JFrame frmToDoList;
	private JTextField datetextField_1;
	private JTextField tasktextField_2;
	private JTextField durationtextField_3;
	private ButtonGroup bg = new ButtonGroup();
	private DefaultTableModel model;
	
	Connection conn = null;
	Statement stat;
	PreparedStatement pst;
	ResultSet rs;
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInput window = new UserInput();
					window.frmToDoList.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInput() {
		conn = dbConnect();  // connect to SQLite Database
		initialize();        // Method call
	}

	private Connection dbConnect() {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");   // Load Java JDBC Driver 
			// Connect to SQLite
			conn= DriverManager.getConnection("JDBC:sqlite:C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\ToDoList.sqlite");
			JOptionPane.showMessageDialog(null, " Successfully Connected to Database :> ");
			return conn;
		}
		catch (Exception err){
			JOptionPane.showMessageDialog(null, "Connection Failed " +err);
			return null;
		}
		
		
	}

	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmToDoList = new JFrame();
		frmToDoList.setTitle("To Do List Calendar");
		frmToDoList.getContentPane().setBackground(SystemColor.activeCaption);
		frmToDoList.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-calendar-40.png"));
		frmToDoList.setBounds(100, 100, 704, 516);
		frmToDoList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToDoList.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "To Do Task", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panel.setBounds(18, 55, 337, 389);
		frmToDoList.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Year:");
		lblNewLabel.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel.setBounds(21, 63, 85, 31);
		panel.add(lblNewLabel);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblMonth.setBounds(21, 97, 85, 31);
		panel.add(lblMonth);
		
		JLabel lblNewLabel_1 = new JLabel("Date:");
		lblNewLabel_1.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel_1.setBounds(21, 128, 85, 31);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Task:");
		lblNewLabel_1_1.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(21, 197, 85, 31);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Status:");
		lblNewLabel_1_1_1.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(21, 278, 54, 31);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Duration:");
		lblNewLabel_1_1_2.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel_1_1_2.setBounds(21, 236, 85, 31);
		panel.add(lblNewLabel_1_1_2);
		
//	ComboBox
		JComboBox monthcomboBox = new JComboBox();
		monthcomboBox.setBounds(77, 101, 96, 22);
		panel.add(monthcomboBox);
		monthcomboBox.addItem("January"); monthcomboBox.addItem("February"); 
		monthcomboBox.addItem("March"); monthcomboBox.addItem("April"); 
		monthcomboBox.addItem("May"); monthcomboBox.addItem("June"); 
		monthcomboBox.addItem("July"); monthcomboBox.addItem("August"); 
		monthcomboBox.addItem("September"); monthcomboBox.addItem("October"); 
		monthcomboBox.addItem("November"); monthcomboBox.addItem("December");
//Date text Field
		datetextField_1 = new JTextField();
		datetextField_1.setBounds(77, 133, 96, 20);
		panel.add(datetextField_1);
		datetextField_1.setColumns(10);
//task text Field	
		tasktextField_2 = new JTextField();
		tasktextField_2.setBounds(77, 202, 158, 20);
		panel.add(tasktextField_2);
		tasktextField_2.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Done");
		rdbtnNewRadioButton.setBackground(SystemColor.inactiveCaption);
		rdbtnNewRadioButton.setBounds(81, 282, 65, 23);
		panel.add(rdbtnNewRadioButton);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Important Task:");
		chckbxNewCheckBox.setBackground(SystemColor.inactiveCaption);
		chckbxNewCheckBox.setFont(new Font("Segoe Script", Font.BOLD, 13));
		chckbxNewCheckBox.setBounds(21, 316, 159, 23);
		panel.add(chckbxNewCheckBox);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Undone");
		rdbtnNewRadioButton_1.setBackground(SystemColor.inactiveCaption);
		rdbtnNewRadioButton_1.setBounds(148, 282, 105, 23);
		panel.add(rdbtnNewRadioButton_1);
	// BUTTONGROUP	
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton);
	//	
		durationtextField_3 = new JTextField();
		durationtextField_3.setBounds(108, 241, 96, 20);
		panel.add(durationtextField_3);
		durationtextField_3.setColumns(10);
		
		JLabel lblNewLabel_1_2 = new JLabel("Day:");
		lblNewLabel_1_2.setFont(new Font("Segoe Script", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(21, 159, 46, 31);
		panel.add(lblNewLabel_1_2);
		
		JComboBox daycomboBox_1 = new JComboBox();
		daycomboBox_1.setBounds(77, 164, 86, 22);
		panel.add(daycomboBox_1); 
	// COMBOBOX DAY 
		daycomboBox_1.addItem("Monday"); daycomboBox_1.addItem("Tuesday"); daycomboBox_1.addItem("Wednesday");
		daycomboBox_1.addItem("Thursday"); daycomboBox_1.addItem("Friday"); daycomboBox_1.addItem("Saturday");
		daycomboBox_1.addItem("Sunday");
		
	// COMBOBOX YEAR 	
		JComboBox comboBoxYear = new JComboBox();
		comboBoxYear.setBounds(77, 68, 96, 21);
		panel.add(comboBoxYear);
		comboBoxYear.addItem("2022");
		for (int i=10; i<99 ; i++)
		comboBoxYear.addItem("20"+i);
	//	ADD TASK
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-add-20.png"));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String year, date, month, day, task, duration,status,important;
				try {
					year= (String) comboBoxYear.getSelectedItem();
					date= datetextField_1.getText(); 
					duration=  durationtextField_3.getText();
					task= tasktextField_2.getText();
					month= (String) monthcomboBox.getSelectedItem();
					day= (String) daycomboBox_1.getSelectedItem();
					status= (rdbtnNewRadioButton.isSelected())? rdbtnNewRadioButton.getLabel():rdbtnNewRadioButton_1.getLabel();
					important= chckbxNewCheckBox.isSelected()? "Important": "Not Important";
					stat= conn.createStatement();
// Add Tasks from the user to the database
					
					stat.executeUpdate("insert into ToDoList (Year, Month,  Date, Day, Task, Duration, Status, Importance)"
							+ "values ( '"+year+"','"+month+"','"+date+"','"+day+"','"+task+"','"+duration+"','"+status+"','"+important+"')");
					JOptionPane.showMessageDialog(null, " Task Added Successfully ");
					
				}
				catch (Exception err) {
					JOptionPane.showMessageDialog(null, " Add Failed " +err);
					err.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(403, 354, 114, 29);
		frmToDoList.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewButton_1.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-cancel-20.png"));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(525, 396, 99, 29);
		frmToDoList.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("To Do List");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-list-20.png"));
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ToDoList newWindow= new ToDoList();
				newWindow.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(403, 396, 114, 29);
		frmToDoList.getContentPane().add(btnNewButton_2);
		
//Clear the textfield		
		JButton btnNewButton_3 = new JButton("Clear");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-clear-20.png"));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datetextField_1.setText("");
				tasktextField_2.setText("");
				durationtextField_3.setText("");
			
			}
		});
		btnNewButton_3.setBounds(525, 354, 99, 29);
		frmToDoList.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Veejay\\Documents\\Java WorkSpace\\1 ToDoList_Calendar PIT\\Icon\\icons8-cat-70.png"));
		lblNewLabel_2.setBounds(8, 0, 92, 60);
		frmToDoList.getContentPane().add(lblNewLabel_2);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(371, 55, 311, 266);
		frmToDoList.getContentPane().add(calendar);
	
	}
}
