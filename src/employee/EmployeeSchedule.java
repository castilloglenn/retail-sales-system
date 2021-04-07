package employee;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Utility;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class EmployeeSchedule extends JDialog {
	
	private JPanel container;
	private JMenuItem themeSwitcher;
	private JPopupMenu popupMenu_1;
	private JMenuItem copyMenu;
	private JSeparator separator;
	private JMenuItem themeSwitcher_1;
	private JPopupMenu popupMenu_2;
	private JMenuItem themeSwitcher_2;
	private JLabel inLabel;
	private JSpinner inSpinner;
	private JLabel outLabel;
	private JSpinner outSpinner;
	private JTable table;
	private JButton removeButton;
	private JButton createButton;
	private JButton exitButton;
	private JButton addButton;
	private JScrollPane scrollPane;
	private JLabel dateLabel;
	private JSpinner dateSpinner;
	private JTextArea textArea;
	private JScrollPane scrollPane_1;
	
	private String[] employeeColumns = {
		"EMPLOYEE ID", "POSITION", "FIRST NAME", "MIDDLE NAME", "LAST NAME", 
		"ADDRESS", "BASIC PAY", "INCENTIVES", "CONTRIBUTIONS", "PENALTY"
	};
	
	private Gallery gl;
	private Utility ut;
	private Database db;
	private Schedule sc;
	private Logger log;
	private long id;
	
	public EmployeeSchedule(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl; this.ut = ut; this.db = db; this.log = log; this.id = id;
		sc = new Schedule(db, log);
		
		setTitle("New Schedule | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 485));
		setSize(700, 485);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPopupMenu popupMenu = new JPopupMenu();
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		addPopup(this, popupMenu);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		container = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(container);
		container.setLayout(null);
		
		addButton = new JButton("ADD");
		addButton.setBounds(10, 335, 148, 34);
		addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(addButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(334, 8, 319, 406);
		container.add(scrollPane);
		
		table = new JTable(1, 10);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(ut.generateTable(db.fetchDataQuery("employee", "employee_id", ""), employeeColumns));
		
		for (int col = 0; col < employeeColumns.length; col++) {
			table.getColumnModel().getColumn(col).setMinWidth(100);
		}
		
		popupMenu_2 = new JPopupMenu();
		addPopup(scrollPane, popupMenu_2);
		
		themeSwitcher_2 = new JMenuItem("Switch to Dark Theme");
		popupMenu_2.add(themeSwitcher_2);
		
		scrollPane.setViewportView(table);
		
		popupMenu_1 = new JPopupMenu();
		addPopup(table, popupMenu_1);
		
		copyMenu = new JMenuItem("Copy");
		popupMenu_1.add(copyMenu);
		
		separator = new JSeparator();
		popupMenu_1.add(separator);
		
		themeSwitcher_1 = new JMenuItem("Switch to Dark Theme");
		popupMenu_1.add(themeSwitcher_1);
		
		removeButton = new JButton("REMOVE");
		removeButton.setBounds(168, 335, 156, 34);
		removeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(removeButton);

		dateLabel = new JLabel("Schedule Date:");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateLabel.setBounds(10, 257, 86, 27);
		container.add(dateLabel);
		
		dateSpinner = new JSpinner(new SpinnerDateModel());
		dateSpinner.setBounds(105, 257, 219, 27);
		SimpleDateFormat model = new SimpleDateFormat("MM/dd/yyyy");
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, model.toPattern()));
		JComponent de_dateSpinner = dateSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)de_dateSpinner;
		spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		container.add(dateSpinner);
		
		createButton = new JButton("<html>CREATE SCHEDULE</html>");
		createButton.setBounds(10, 380, 148, 34);
		createButton.setMargin(new Insets(2, 2, 2, 2));
		createButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(createButton);
		
		exitButton = new JButton("DISCARD SCHEDULE");
		exitButton.setBounds(168, 380, 156, 34);
		exitButton.setMargin(new Insets(2, 2, 2, 2));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(exitButton);
		
		inLabel = new JLabel("IN");
		inLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		inLabel.setBounds(10, 295, 22, 14);
		container.add(inLabel);
		
		inSpinner = new JSpinner(new SpinnerDateModel());
		SimpleDateFormat model2 = new SimpleDateFormat("hh:mm aa");
		inSpinner.setEditor(new JSpinner.DateEditor(inSpinner, model2.toPattern()));
		JComponent de_inSpinner = inSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor2 = (JSpinner.DefaultEditor)de_inSpinner;
		spinnerEditor2.getTextField().setHorizontalAlignment(JTextField.CENTER);
		inSpinner.setBounds(31, 290, 127, 27);
		container.add(inSpinner);
		
		outLabel = new JLabel("OUT");
		outLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		outLabel.setBounds(168, 295, 30, 14);
		container.add(outLabel);
		
		outSpinner = new JSpinner(new SpinnerDateModel());
		SimpleDateFormat model3 = new SimpleDateFormat("hh:mm aa");
		outSpinner.setEditor(new JSpinner.DateEditor(outSpinner, model3.toPattern()));
		JComponent de_outSpinner = outSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor3 = (JSpinner.DefaultEditor)de_outSpinner;
		spinnerEditor3.getTextField().setHorizontalAlignment(JTextField.CENTER);
		outSpinner.setBounds(197, 290, 127, 27);
		container.add(outSpinner);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 8, 312, 240);
		container.add(scrollPane_1);
		
		textArea = new JTextArea(sc.format());
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);

		copyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ut.copyToClipboard(
						table.getValueAt(
						table.getSelectedRow(), 
						table.getSelectedColumn()
						).toString()
					);
				} catch (ArrayIndexOutOfBoundsException e1) {}
			}
		});
		dateSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
				String dateValue = dateFormater.format(dateSpinner.getValue());
				
				sc.setDate(dateValue);
				textArea.setText(sc.format());
				textArea.setCaretPosition(0);
			}
		});
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long id = Long.parseLong(table.getValueAt(table.getSelectedRow(), 0).toString());
					SimpleDateFormat timeFormater = new SimpleDateFormat("HH:mm");
					String inValue = timeFormater.format(inSpinner.getValue());
					String outValue = timeFormater.format(outSpinner.getValue());
					
					if (!sc.add(id, inValue, outValue)) {
						JOptionPane.showMessageDialog(null, 
							"Please remove the employee before inserting it again.",
							"Already exists | " + Main.SYSTEM_NAME, 
							JOptionPane.WARNING_MESSAGE);
					} else {
						textArea.setText(sc.format());
						textArea.setCaretPosition(0);
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, 
						"Please select an employee row from the table provided.",
						"Unknown Selection | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, 
						"Please place back the employee id as the first column in the table.",
						"Invalid Column | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long id = Long.parseLong(table.getValueAt(table.getSelectedRow(), 0).toString());
					
					if (!sc.remove(id)) {
						JOptionPane.showMessageDialog(null, 
							"The employee has no schedule.",
							"No Schedule | " + Main.SYSTEM_NAME, 
							JOptionPane.WARNING_MESSAGE);
					} else {
						textArea.setText(sc.format());
						textArea.setCaretPosition(0);
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, 
						"Please select an employee row from the table provided.",
						"Unknown Selection | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, 
						"Please place back the employee id as the first column in the table.",
						"Invalid Column | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String check = sc.check();
				if (check == null) {
					if (sc.log(id)) {
						JOptionPane.showMessageDialog(null, 
							"Successfully created and distributed the schedule.",
							"Success | " + Main.SYSTEM_NAME, 
							JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, 
						check,
						"Invalid schedule | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		themeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		themeSwitcher_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		themeSwitcher_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				adjustTheme(false);
			}
		});
		
		adjustTheme(false);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void adjustTheme(boolean change) {
		if (change) gl.isDark = (gl.isDark) ? false : true;
		
		gl.designOptionPanes();
		gl.getAllComponentsChangeTheme(this, 7);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
