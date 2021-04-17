package employee;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Utility;

import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import java.awt.Insets;
import java.awt.event.WindowAdapter;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import main.Main;
import main.Portal;

import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class EmployeeAdmin extends JFrame {

	private JPanel container, contentPane, title, navigation, display, dashboard, manage,
		payroll, schedule, manageCrud;
	private JToggleButton dashboardLabel, 
		manageLabel, scheduleLabel, payrollLabel;
	private JLabel photo, titleTitle, dashboardTitle, scheduleTitle, payrollTitle, manageTitle, payrollInstruction,
		scheduleInstruction;
	private JButton manageSearchButton, manageAddButton, manageUpdateButton, manageDeleteButton,
		payrollGenerate, payrollDelete, scheduleViewButton;
	private JScrollPane manageScrollPane, payrollScrollPane;
	private JMenuItem themeSwitcher, themeSwitcher_1;
	private JComboBox<String> manageSearchCategory;
	private JPopupMenu popupMenu_1, popupMenu_2;
	private JTable manageTable, payrollTable;
	private JTextField manageSearchField;
	private SpringLayout sl_contentPane;
	private JMenuItem themeSwitcher_2;
	private JTextArea dashboardArea;
	private JTable scheduleTable;
	private JSeparator separator;
	private CardLayout cl;
	
	private String[] employeeColumns = {
		"EMPLOYEE ID", "POSITION", "FIRST NAME", "MIDDLE NAME", "LAST NAME", 
		"ADDRESS", "BASIC PAY", "INCENTIVES", "CONTRIBUTIONS", "PENALTY"
	};
	private String[] columnNames = {
		"employee_id", "position", "fname", "mname", "lname", "address", 
		"basic", "incentives", "contributions", "penalty"
	};
	private String[] searchableColumns = {
		columnNames[0], columnNames[1], columnNames[2], 
		columnNames[3], columnNames[4], columnNames[5]
	};
	private String[] frontEndColumnNames = {
		employeeColumns[0], employeeColumns[1], employeeColumns[2], 
		employeeColumns[3], employeeColumns[4], employeeColumns[5]
	};
	private String[] scheduleColumns = {
		"ID", "DATE", "SCHEDULE", "CREATED BY"
	};
	private String[] payrollColumns = {
			"ID", "DATE", "PAYROLL", "CREATED BY"
		};
	
	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public EmployeeAdmin(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl; this.ut = ut; this.db = db;  
		
		setTitle("Employee Management | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 480));
		setSize(900, 550);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		addPopup(this, popupMenu);
		
		contentPane = new JPanel();
		contentPane.setName("Frame");
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		container = new JPanel();
		container.setName("Frame");
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		SpringLayout sl_container = new SpringLayout();
		container.setLayout(sl_container);
		
		title = new JPanel();
		sl_container.putConstraint(SpringLayout.SOUTH, title, 40, SpringLayout.NORTH, container);
		title.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sl_container.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, container);
		sl_container.putConstraint(SpringLayout.WEST, title, 0, SpringLayout.WEST, container);
		sl_container.putConstraint(SpringLayout.EAST, title, 0, SpringLayout.EAST, container);
		container.add(title);
		
		navigation = new JPanel();
		navigation.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sl_container.putConstraint(SpringLayout.NORTH, navigation, 10, SpringLayout.SOUTH, title);
		sl_container.putConstraint(SpringLayout.WEST, navigation, 0, SpringLayout.WEST, container);
		sl_container.putConstraint(SpringLayout.SOUTH, navigation, 0, SpringLayout.SOUTH, container);
		sl_container.putConstraint(SpringLayout.EAST, navigation, 125, SpringLayout.WEST, container);
		container.add(navigation);
		
		display = new JPanel();
		sl_container.putConstraint(SpringLayout.NORTH, display, 10, SpringLayout.SOUTH, title);
		
		titleTitle = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
		titleTitle.setHorizontalAlignment(SwingConstants.CENTER);
		titleTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.add(titleTitle);
		sl_container.putConstraint(SpringLayout.WEST, display, 10, SpringLayout.EAST, navigation);
		SpringLayout sl_navigation = new SpringLayout();
		navigation.setLayout(sl_navigation);
		
		dashboardLabel = new JToggleButton("DASHBOARD");
		dashboardLabel.setFocusable(false);
		dashboardLabel.setEnabled(false);
		dashboardLabel.setSelected(true);
		dashboardLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		dashboardLabel.setMargin(new Insets(10, 0, 10, 0));
		dashboardLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.WEST, dashboardLabel, 10, SpringLayout.WEST, navigation);
		sl_navigation.putConstraint(SpringLayout.EAST, dashboardLabel, -10, SpringLayout.EAST, navigation);
		navigation.add(dashboardLabel);
		
		manageLabel = new JToggleButton("MANAGE");
		sl_navigation.putConstraint(SpringLayout.WEST, manageLabel, 10, SpringLayout.WEST, navigation);
		sl_navigation.putConstraint(SpringLayout.EAST, manageLabel, -10, SpringLayout.EAST, navigation);
		manageLabel.setFocusable(false);
		manageLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		manageLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.SOUTH, manageLabel, -10, SpringLayout.SOUTH, navigation);
		manageLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		navigation.add(manageLabel);
		
		scheduleLabel = new JToggleButton("SCHEDULE");
		scheduleLabel.setFocusable(false);
		scheduleLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scheduleLabel.setMargin(new Insets(10, 0, 10, 0));
		scheduleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.WEST, scheduleLabel, 0, SpringLayout.WEST, dashboardLabel);
		sl_navigation.putConstraint(SpringLayout.EAST, scheduleLabel, 0, SpringLayout.EAST, manageLabel);
		navigation.add(scheduleLabel);
		
		payrollLabel = new JToggleButton("PAYROLL");
		payrollLabel.setFocusable(false);
		payrollLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		payrollLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.SOUTH, scheduleLabel, -6, SpringLayout.NORTH, payrollLabel);
		sl_navigation.putConstraint(SpringLayout.WEST, payrollLabel, 10, SpringLayout.WEST, navigation);
		sl_navigation.putConstraint(SpringLayout.EAST, payrollLabel, -10, SpringLayout.EAST, navigation);
		sl_navigation.putConstraint(SpringLayout.SOUTH, payrollLabel, -6, SpringLayout.NORTH, manageLabel);
		payrollLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		navigation.add(payrollLabel);
		
		photo = new JLabel((gl.isDark) ? gl.darkEmployee : gl.lightEmployee);
		sl_navigation.putConstraint(SpringLayout.NORTH, dashboardLabel, 10, SpringLayout.SOUTH, photo);
		sl_navigation.putConstraint(SpringLayout.NORTH, photo, 10, SpringLayout.NORTH, navigation);
		sl_navigation.putConstraint(SpringLayout.WEST, photo, 10, SpringLayout.WEST, navigation);
		sl_navigation.putConstraint(SpringLayout.EAST, photo, -10, SpringLayout.EAST, navigation);
		navigation.add(photo);
		sl_container.putConstraint(SpringLayout.SOUTH, display, 0, SpringLayout.SOUTH, container);
		sl_container.putConstraint(SpringLayout.EAST, display, 0, SpringLayout.EAST, container);
		container.add(display);
		cl = new CardLayout(0, 0);
		display.setLayout(cl);
		
		dashboard = new JPanel();
		dashboard.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(dashboard, "dashboard");
		SpringLayout sl_dashboard = new SpringLayout();
		dashboard.setLayout(sl_dashboard);
		
		dashboardTitle = new JLabel("DASHBOARD");
		dashboardTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_dashboard.putConstraint(SpringLayout.NORTH, dashboardTitle, 10, SpringLayout.NORTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.WEST, dashboardTitle, 10, SpringLayout.WEST, dashboard);
		dashboard.add(dashboardTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_dashboard.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, dashboardTitle);
		sl_dashboard.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, dashboard);
		sl_dashboard.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, dashboard);
		dashboard.add(scrollPane);
		
		dashboardArea = new JTextArea();
		dashboardArea.setWrapStyleWord(true);
		dashboardArea.setFocusable(false);
		dashboardArea.setMargin(new Insets(10, 10, 10, 10));
		dashboardArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(dashboardArea);
		
		schedule = new JPanel();
		schedule.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(schedule, "schedule");
		SpringLayout sl_schedule = new SpringLayout();
		schedule.setLayout(sl_schedule);
		
		scheduleTitle = new JLabel("SCHEDULE");
		scheduleTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleTitle, 10, SpringLayout.NORTH, schedule);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleTitle, 10, SpringLayout.WEST, schedule);
		schedule.add(scheduleTitle);
		
		JScrollPane scheduleScrollPane = new JScrollPane();
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleScrollPane, 10, SpringLayout.SOUTH, scheduleTitle);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleScrollPane, 10, SpringLayout.WEST, schedule);
		sl_schedule.putConstraint(SpringLayout.SOUTH, scheduleScrollPane, -10, SpringLayout.SOUTH, schedule);
		schedule.add(scheduleScrollPane);
		
		scheduleViewButton = new JButton("VIEW");
		scheduleViewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_schedule.putConstraint(SpringLayout.EAST, scheduleScrollPane, -10, SpringLayout.WEST, scheduleViewButton);
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleViewButton, 0, SpringLayout.NORTH, scheduleScrollPane);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleViewButton, -150, SpringLayout.EAST, schedule);
		sl_schedule.putConstraint(SpringLayout.SOUTH, scheduleViewButton, 50, SpringLayout.NORTH, scheduleScrollPane);
		
		popupMenu_2 = new JPopupMenu();
		addPopup(scheduleScrollPane, popupMenu_2);
		
		themeSwitcher_2 = new JMenuItem("Switch to Dark Theme");
		popupMenu_2.add(themeSwitcher_2);
		
		scheduleTable = new JTable(1, 4);
		scheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleScrollPane.setViewportView(scheduleTable);
		sl_schedule.putConstraint(SpringLayout.EAST, scheduleViewButton, -10, SpringLayout.EAST, schedule);
		schedule.add(scheduleViewButton);
		
		JButton scheduleCreateButton = new JButton("CREATE NEW");
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleCreateButton, 6, SpringLayout.SOUTH, scheduleViewButton);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleCreateButton, 0, SpringLayout.WEST, scheduleViewButton);
		sl_schedule.putConstraint(SpringLayout.SOUTH, scheduleCreateButton, 56, SpringLayout.SOUTH, scheduleViewButton);
		sl_schedule.putConstraint(SpringLayout.EAST, scheduleCreateButton, 0, SpringLayout.EAST, scheduleViewButton);
		scheduleCreateButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		schedule.add(scheduleCreateButton);
		
		JButton scheduleDeleteButton = new JButton("DELETE");
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleDeleteButton, 6, SpringLayout.SOUTH, scheduleCreateButton);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleDeleteButton, 0, SpringLayout.WEST, scheduleCreateButton);
		sl_schedule.putConstraint(SpringLayout.SOUTH, scheduleDeleteButton, 56, SpringLayout.SOUTH, scheduleCreateButton);
		sl_schedule.putConstraint(SpringLayout.EAST, scheduleDeleteButton, 0, SpringLayout.EAST, scheduleCreateButton);
		scheduleDeleteButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		schedule.add(scheduleDeleteButton);
		
		scheduleInstruction = new JLabel(
			"<html>Select a row and press the \"View\" to see the schedule in full form.</html>"
		);
		scheduleInstruction.setBorder(new TitledBorder(null, "Instructions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scheduleInstruction.setVerticalAlignment(SwingConstants.TOP);
		scheduleInstruction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_schedule.putConstraint(SpringLayout.NORTH, scheduleInstruction, 6, SpringLayout.SOUTH, scheduleDeleteButton);
		sl_schedule.putConstraint(SpringLayout.WEST, scheduleInstruction, 0, SpringLayout.WEST, scheduleDeleteButton);
		sl_schedule.putConstraint(SpringLayout.SOUTH, scheduleInstruction, -10, SpringLayout.SOUTH, schedule);
		sl_schedule.putConstraint(SpringLayout.EAST, scheduleInstruction, 0, SpringLayout.EAST, scheduleDeleteButton);
		schedule.add(scheduleInstruction);
		
		payroll = new JPanel();
		payroll.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(payroll, "payroll");
		SpringLayout sl_payroll = new SpringLayout();
		payroll.setLayout(sl_payroll);
		
		payrollTitle = new JLabel("PAYROLL");
		payrollTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollTitle, 10, SpringLayout.NORTH, payroll);
		sl_payroll.putConstraint(SpringLayout.WEST, payrollTitle, 10, SpringLayout.WEST, payroll);
		payroll.add(payrollTitle);
		
		payrollScrollPane = new JScrollPane();
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollScrollPane, 10, SpringLayout.SOUTH, payrollTitle);
		sl_payroll.putConstraint(SpringLayout.WEST, payrollScrollPane, 10, SpringLayout.WEST, payroll);
		sl_payroll.putConstraint(SpringLayout.SOUTH, payrollScrollPane, -10, SpringLayout.SOUTH, payroll);
		payroll.add(payrollScrollPane);
		
		payrollGenerate = new JButton("GENERATE");
		sl_payroll.putConstraint(SpringLayout.WEST, payrollGenerate, -150, SpringLayout.EAST, payroll);
		sl_payroll.putConstraint(SpringLayout.SOUTH, payrollGenerate, 50, SpringLayout.NORTH, payrollScrollPane);
		sl_payroll.putConstraint(SpringLayout.EAST, payrollScrollPane, -10, SpringLayout.WEST, payrollGenerate);
		payrollGenerate.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollGenerate, 0, SpringLayout.NORTH, payrollScrollPane);
		sl_payroll.putConstraint(SpringLayout.EAST, payrollGenerate, -10, SpringLayout.EAST, payroll);
		payroll.add(payrollGenerate);
		
		payrollDelete = new JButton("DELETE");
		sl_payroll.putConstraint(SpringLayout.WEST, payrollDelete, 0, SpringLayout.WEST, payrollGenerate);
		sl_payroll.putConstraint(SpringLayout.SOUTH, payrollDelete, 106, SpringLayout.NORTH, payrollScrollPane);
		
		payrollTable = new JTable(1, 4);
		payrollScrollPane.setViewportView(payrollTable);
		payrollDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollDelete, 6, SpringLayout.SOUTH, payrollGenerate);
		sl_payroll.putConstraint(SpringLayout.EAST, payrollDelete, 0, SpringLayout.EAST, payrollGenerate);
		payroll.add(payrollDelete);
		
		payrollInstruction = new JLabel(
			  "<html>"
			+ "Press \"GENERATE\" to create new payroll from the previous payroll's end date."
			+ " To delete a payroll, click one from the table and press the \"DELETE\" button."
			+ "</html>");
		payrollInstruction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		payrollInstruction.setVerticalAlignment(SwingConstants.TOP);
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollInstruction, 6, SpringLayout.SOUTH, payrollDelete);
		sl_payroll.putConstraint(SpringLayout.WEST, payrollInstruction, 0, SpringLayout.WEST, payrollGenerate);
		sl_payroll.putConstraint(SpringLayout.SOUTH, payrollInstruction, -10, SpringLayout.SOUTH, payroll);
		sl_payroll.putConstraint(SpringLayout.EAST, payrollInstruction, 0, SpringLayout.EAST, payrollGenerate);
		payroll.add(payrollInstruction);
		
		manage = new JPanel();
		manage.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(manage, "manage");
		SpringLayout sl_manage = new SpringLayout();
		manage.setLayout(sl_manage);
		
		manageTitle = new JLabel("MANAGE EMPLOYEES");
		manageTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_manage.putConstraint(SpringLayout.NORTH, manageTitle, 10, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, manageTitle, 10, SpringLayout.WEST, manage);
		manage.add(manageTitle);
		
		manageSearchCategory = new JComboBox<String>();
		sl_manage.putConstraint(SpringLayout.NORTH, manageSearchCategory, 10, SpringLayout.SOUTH, manageTitle);
		manageSearchCategory.setModel(new DefaultComboBoxModel<String>(frontEndColumnNames));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		manageSearchCategory.setRenderer(listRenderer);
		sl_manage.putConstraint(SpringLayout.WEST, manageSearchCategory, 0, SpringLayout.WEST, manageTitle);
		manage.add(manageSearchCategory);
		
		manageSearchField = new JTextField();
		sl_manage.putConstraint(SpringLayout.NORTH, manageSearchField, 0, SpringLayout.NORTH, manageSearchCategory);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageSearchField, 0, SpringLayout.SOUTH, manageSearchCategory);
		manageSearchField.setMargin(new Insets(2, 10, 2, 10));
		sl_manage.putConstraint(SpringLayout.WEST, manageSearchField, 6, SpringLayout.EAST, manageSearchCategory);
		manage.add(manageSearchField);
		manageSearchField.setColumns(10);
		
		manageSearchButton = new JButton("SEARCH");
		sl_manage.putConstraint(SpringLayout.EAST, manageSearchField, -6, SpringLayout.WEST, manageSearchButton);
		manageSearchButton.setMargin(new Insets(2, 2, 2, 2));
		sl_manage.putConstraint(SpringLayout.NORTH, manageSearchButton, 0, SpringLayout.NORTH, manageSearchField);
		sl_manage.putConstraint(SpringLayout.WEST, manageSearchButton, -100, SpringLayout.EAST, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageSearchButton, 0, SpringLayout.SOUTH, manageSearchField);
		sl_manage.putConstraint(SpringLayout.EAST, manageSearchButton, -10, SpringLayout.EAST, manage);
		manage.add(manageSearchButton);
		
		manageScrollPane = new JScrollPane();
		sl_manage.putConstraint(SpringLayout.NORTH, manageScrollPane, 10, SpringLayout.SOUTH, manageSearchCategory);
		sl_manage.putConstraint(SpringLayout.WEST, manageScrollPane, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, manageScrollPane, -10, SpringLayout.EAST, manage);
		manage.add(manageScrollPane);
		addPopup(manageScrollPane, popupMenu);
		
		manageCrud = new JPanel();
		sl_manage.putConstraint(SpringLayout.NORTH, manageCrud, -35, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageScrollPane, -10, SpringLayout.NORTH, manageCrud);
		
		manageTable = new JTable(1, 10);
		manageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		manageScrollPane.setViewportView(manageTable);
		
		popupMenu_1 = new JPopupMenu();
		addPopup(manageTable, popupMenu_1);
		
		JMenuItem copyMenu = new JMenuItem("Copy");
		popupMenu_1.add(copyMenu);
		
		separator = new JSeparator();
		popupMenu_1.add(separator);
		
		themeSwitcher_1 = new JMenuItem("Switch to Dark Theme");
		popupMenu_1.add(themeSwitcher_1);
		sl_manage.putConstraint(SpringLayout.WEST, manageCrud, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageCrud, -10, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.EAST, manageCrud, -10, SpringLayout.EAST, manage);
		manage.add(manageCrud);
		manageCrud.setLayout(new GridLayout(0, 3, 10, 0));
		
		manageAddButton = new JButton("ADD NEW");
		manageCrud.add(manageAddButton);
		
		manageUpdateButton = new JButton("UPDATE");
		manageCrud.add(manageUpdateButton);
		
		manageDeleteButton = new JButton("DELETE");
		manageCrud.add(manageDeleteButton);

		copyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ut.copyToClipboard(
						manageTable.getValueAt(
							manageTable.getSelectedRow(), 
							manageTable.getSelectedColumn()
						).toString()
					);
				} catch (ArrayIndexOutOfBoundsException e1) {}
			}
		});
		dashboardLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(dashboardLabel);
				cl.show(display, "dashboard");
			}
		});
		scheduleLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(scheduleLabel);
				cl.show(display, "schedule");
			}
		});
		scheduleViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String desc = scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 2).toString();
					JOptionPane.showMessageDialog(null, 
						desc,
						"View Schedule | " + Main.SYSTEM_NAME, 
						JOptionPane.INFORMATION_MESSAGE);
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, 
						"Please select a schedule row from the table provided.",
						"Unknown Selection | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		scheduleCreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeSchedule(gl, ut, db, log, id);
			}
		});
		scheduleDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long id = Long.parseLong(scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 0).toString());
					String date = scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 1).toString();
					String desc = scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 2).toString();
					int res = JOptionPane.showConfirmDialog(null, 
						desc, 
						"Delete Schedule? | " + Main.SYSTEM_NAME, 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (res == 0) {
						if (log.removeLog(id, date)) {
							JOptionPane.showMessageDialog(null, 
								"Schedule deleted successfully.",
								"Schedule Deleted | " + Main.SYSTEM_NAME, 
								JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, 
						"Please select a schedule row from the table provided.",
						"Unknown Selection | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		payrollLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(payrollLabel);
				cl.show(display, "payroll");
			}
		});
		payrollGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Payroll(db, log, ut, id);
			}
		});
		payrollDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long id = Long.parseLong(payrollTable.getValueAt(payrollTable.getSelectedRow(), 0).toString());
					String date = payrollTable.getValueAt(payrollTable.getSelectedRow(), 1).toString();
					String desc = payrollTable.getValueAt(payrollTable.getSelectedRow(), 2).toString();
					int res = JOptionPane.showConfirmDialog(null, 
						desc, 
						"Delete Payroll? | " + Main.SYSTEM_NAME, 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (res == 0) {
						if (log.removeLog(id, date)) {
							JOptionPane.showMessageDialog(null, 
								"Payroll deleted successfully.",
								"Payroll Deleted | " + Main.SYSTEM_NAME, 
								JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, 
						"Please select a payroll row from the table provided.",
						"Unknown Selection | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		manageLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(manageLabel);
				cl.show(display, "manage");
			}
		});
		manageSearchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					manageSearchButton.doClick();
				}
			}
		});
		manageSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageTable.setModel(
					queryDatabase(
						searchableColumns[manageSearchCategory.getSelectedIndex()], 
						manageSearchField.getText())
				);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				for(int col=0; col < 10; col++){
					manageTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
			    };
			}
		});
		manageAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeNew(gl, ut, db);
			}
		});
		manageUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeUpdate(gl, ut, db);
			}
		});
		manageDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeDelete(gl, ut, db);
			}
		});
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == 0) {
					adjustContainer();
					adjustFonts();
				}
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustContainer();
				adjustFonts();
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
				dashboardArea.setText(log.getDashboard());
				dashboardArea.setCaretPosition(0);
				manageTable.setModel(queryDatabase("employee_id", ""));
				scheduleTable.setModel(ut.generateTable(log.fetchSchedules(), scheduleColumns));
				payrollTable.setModel(ut.generateTable(log.fetchPayrolls(), payrollColumns));
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				for(int col=0; col < 10; col++){
					manageTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
					if (col < 4) {
						scheduleTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
						payrollTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
					}
			    };
				adjustTheme(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				new Portal(gl, ut, db, log, id);
			}
		});
		
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dashboardArea.setText(log.getDashboard());
				dashboardArea.setCaretPosition(0);
			}
		});
		timer.start();
		
		adjustTheme(false);
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
	
	private DefaultTableModel queryDatabase(String column, String query) {
		Object[][] rows = db.fetchDataQuery("employee", column, query, column, "ASC");
		if (rows == null) return ut.generateTable(new Object[][] {{}}, employeeColumns);
		
		Object[][] data = new Object[rows.length][10];
		int index = 0;
		for (Object[] columns : rows) {
			Object[] pickedDatas = new Object[10];
			pickedDatas[0] = (long) columns[0]; 
			pickedDatas[1] = columns[1].toString(); 
			pickedDatas[2] = columns[2].toString();
			pickedDatas[3] = (columns[3] == null) ? "" : columns[3].toString();
			pickedDatas[4] = columns[4].toString();
			pickedDatas[5] = columns[5].toString();
			pickedDatas[6] = (double) columns[6];
			pickedDatas[7] = (double) columns[7];
			pickedDatas[8] = (double) columns[8];
			pickedDatas[9] = (double) columns[9];
			data[index] = pickedDatas;
			index++;
		}
		return ut.generateTable(data, employeeColumns);
	}
	
	private void adjustFonts() {
		int minTitle = 604;
		int minDisplay = 469;
		
		ut.adjustFont(titleTitle, title, minTitle, 18);
		ut.adjustFont(dashboardTitle, dashboard, minDisplay, 16);
		ut.adjustFont(scheduleTitle, schedule, minDisplay, 16);
		ut.adjustFont(payrollTitle, payroll, minDisplay, 16);
		ut.adjustFont(manageTitle, manage, minDisplay, 16);
		ut.adjustFont(manageSearchCategory, manage, minDisplay, 11);
		ut.adjustFont(manageSearchField, manage, minDisplay, 11);
		ut.adjustFont(manageSearchButton, manage, minDisplay, 11);
		ut.adjustFont(manageAddButton, manage, minDisplay, 11);
		ut.adjustFont(manageUpdateButton, manage, minDisplay, 11);
		ut.adjustFont(manageDeleteButton, manage, minDisplay, 11);
		ut.adjustFont(payrollGenerate, payroll, minDisplay, 12);
		ut.adjustFont(payrollDelete, payroll, minDisplay, 12);
	}
	
	private void adjustContainer() {
		int maxWidth = 1100;
		int maxHeight = 600;

		int width = container.getSize().width;
		int height = container.getSize().height;
		
		int widthOverflow = ((width - maxWidth) / 2 < 10) ? 10 : (width - maxWidth) / 2;
		int heightOverflow = ((height - maxHeight) / 2 < 10) ? 10 : (height - maxHeight) / 2;
		
		sl_contentPane.putConstraint(SpringLayout.WEST, container, widthOverflow, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -widthOverflow, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, heightOverflow, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -heightOverflow, SpringLayout.SOUTH, contentPane);
	}
	
	private void adjustTheme(boolean change) {
		if (change) gl.isDark = (gl.isDark) ? false : true;
		
		gl.designOptionPanes();
		gl.getAllComponentsChangeTheme(this, 9);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		payrollInstruction.setBorder(
			new TitledBorder(
				null, "Instructions", TitledBorder.LEADING, TitledBorder.TOP, 
				null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		scheduleInstruction.setBorder(
			new TitledBorder(
				null, "Instructions", TitledBorder.LEADING, TitledBorder.TOP, 
				null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		
		if (gl.isDark) {
			photo.setIcon(gl.darkEmployee);
		} else {
			photo.setIcon(gl.lightEmployee);
		}
	}
	
	private void toggleOne(JToggleButton enabled) {
		JToggleButton[] list = {dashboardLabel, manageLabel, scheduleLabel, payrollLabel};
		for (JToggleButton b : list) {
			if (b != enabled) {
				b.setSelected(false);
				b.setEnabled(true);
			}
		}
		enabled.setEnabled(false);
	}
}
