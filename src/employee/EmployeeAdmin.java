package employee;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import utils.Database;
import utils.Gallery;
import utils.Utility;

import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import java.awt.Insets;
import java.awt.event.WindowAdapter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import main.Main;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class EmployeeAdmin extends JFrame {

	private JPanel container, contentPane, title, navigation, display, dashboard, manage,
		payroll, schedule, log, attendance, notification;
	private JToggleButton dashboardLabel, notificationLabel, attendanceLabel, logLabel, 
		manageLabel, scheduleLabel, payrollLabel;
	private JLabel photo, titleTitle, dashboardTitle, notificationTitle, attendanceTitle,
		logTitle, scheduleTitle, payrollTitle, manageTitle;
	private JButton manageSearchButton, manageAddButton, manageUpdateButton, manageDeleteButton;
	private JTextField manageSearchField;
	private JMenuItem themeSwitcher, themeSwitcher_1;
	private JComboBox<String> manageSearchCategory;
	private SpringLayout sl_contentPane;
	private JPopupMenu popupMenu_1;
	private JSeparator separator;
	private JTable manageTable;
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
	
	private Gallery gl;
	private Utility ut;
	private Database db;
	
	private long id;

	public EmployeeAdmin(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		id = db.fetchLastIDByTable("employee", "employee_id");
		
		setTitle("Employee Management | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 480));
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		notificationLabel = new JToggleButton("NOTIFICATIONS");
		notificationLabel.setFocusable(false);
		notificationLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		notificationLabel.setMargin(new Insets(10, 0, 10, 0));
		notificationLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.NORTH, notificationLabel, 6, SpringLayout.SOUTH, dashboardLabel);
		sl_navigation.putConstraint(SpringLayout.WEST, notificationLabel, 0, SpringLayout.WEST, dashboardLabel);
		sl_navigation.putConstraint(SpringLayout.EAST, notificationLabel, 0, SpringLayout.EAST, dashboardLabel);
		navigation.add(notificationLabel);
		
		attendanceLabel = new JToggleButton("ATTENDANCE");
		attendanceLabel.setFocusable(false);
		attendanceLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		attendanceLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.EAST, attendanceLabel, 0, SpringLayout.EAST, notificationLabel);
		attendanceLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.WEST, attendanceLabel, 0, SpringLayout.WEST, dashboardLabel);
		navigation.add(attendanceLabel);
		
		logLabel = new JToggleButton("LOGS");
		logLabel.setFocusable(false);
		logLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		logLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.SOUTH, attendanceLabel, -6, SpringLayout.NORTH, logLabel);
		sl_navigation.putConstraint(SpringLayout.EAST, logLabel, 0, SpringLayout.EAST, attendanceLabel);
		logLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.WEST, logLabel, 0, SpringLayout.WEST, dashboardLabel);
		navigation.add(logLabel);
		
		manageLabel = new JToggleButton("MANAGE");
		manageLabel.setFocusable(false);
		manageLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		manageLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.SOUTH, manageLabel, -10, SpringLayout.SOUTH, navigation);
		sl_navigation.putConstraint(SpringLayout.EAST, manageLabel, 0, SpringLayout.EAST, logLabel);
		manageLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_navigation.putConstraint(SpringLayout.WEST, manageLabel, 0, SpringLayout.WEST, dashboardLabel);
		navigation.add(manageLabel);
		
		scheduleLabel = new JToggleButton("SCHEDULE");
		scheduleLabel.setFocusable(false);
		scheduleLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scheduleLabel.setMargin(new Insets(10, 0, 10, 0));
		sl_navigation.putConstraint(SpringLayout.SOUTH, logLabel, -6, SpringLayout.NORTH, scheduleLabel);
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
		
		notification = new JPanel();
		notification.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(notification, "notification");
		SpringLayout sl_notification = new SpringLayout();
		notification.setLayout(sl_notification);
		
		notificationTitle = new JLabel("NOTIFICATIONS");
		notificationTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_notification.putConstraint(SpringLayout.NORTH, notificationTitle, 10, SpringLayout.NORTH, notification);
		sl_notification.putConstraint(SpringLayout.WEST, notificationTitle, 10, SpringLayout.WEST, notification);
		notification.add(notificationTitle);
		
		attendance = new JPanel();
		attendance.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(attendance, "attendance");
		SpringLayout sl_attendance = new SpringLayout();
		attendance.setLayout(sl_attendance);
		
		attendanceTitle = new JLabel("ATTENDANCE");
		attendanceTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_attendance.putConstraint(SpringLayout.NORTH, attendanceTitle, 10, SpringLayout.NORTH, attendance);
		sl_attendance.putConstraint(SpringLayout.WEST, attendanceTitle, 10, SpringLayout.WEST, attendance);
		attendance.add(attendanceTitle);
		
		log = new JPanel();
		log.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		display.add(log, "log");
		SpringLayout sl_log = new SpringLayout();
		log.setLayout(sl_log);
		
		logTitle = new JLabel("LOGS");
		logTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		sl_log.putConstraint(SpringLayout.NORTH, logTitle, 10, SpringLayout.NORTH, log);
		sl_log.putConstraint(SpringLayout.WEST, logTitle, 10, SpringLayout.WEST, log);
		log.add(logTitle);
		
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
		
		JScrollPane manageScrollPane = new JScrollPane();
		sl_manage.putConstraint(SpringLayout.NORTH, manageScrollPane, 10, SpringLayout.SOUTH, manageSearchCategory);
		sl_manage.putConstraint(SpringLayout.WEST, manageScrollPane, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, manageScrollPane, -10, SpringLayout.EAST, manage);
		manage.add(manageScrollPane);
		addPopup(manageScrollPane, popupMenu);
		
		JPanel manageCrud = new JPanel();
		sl_manage.putConstraint(SpringLayout.NORTH, manageCrud, -35, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageScrollPane, -10, SpringLayout.NORTH, manageCrud);
		
		manageTable = new JTable(1, 10);
		manageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		manageScrollPane.setViewportView(manageTable);
		
		popupMenu_1 = new JPopupMenu();
		addPopup(manageTable, popupMenu_1);
		
		themeSwitcher_1 = new JMenuItem("Switch to Dark Theme");
		popupMenu_1.add(themeSwitcher_1);
		
		separator = new JSeparator();
		popupMenu_1.add(separator);
		
		JMenuItem copyMenu = new JMenuItem("Copy");
		popupMenu_1.add(copyMenu);
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

		
		dashboardLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(dashboardLabel);
				cl.show(display, "dashboard");
			}
		});
		notificationLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(notificationLabel);
				cl.show(display, "notification");
			}
		});
		attendanceLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(attendanceLabel);
				cl.show(display, "attendance");
			}
		});
		logLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(logLabel);
				cl.show(display, "log");
			}
		});
		scheduleLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(scheduleLabel);
				cl.show(display, "schedule");
			}
		});
		payrollLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleOne(payrollLabel);
				cl.show(display, "payroll");
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
		copyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ut.copyToClipboard(
					manageTable.getValueAt(
						manageTable.getSelectedRow(), 
						manageTable.getSelectedColumn()
					).toString()
				);
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
		themeSwitcher_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		themeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				manageTable.setModel(queryDatabase("employee_id", ""));
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				for(int col=0; col < 10; col++){
					manageTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
			    };
				adjustTheme(false);
			}
		});
		
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
		Object[][] rows = db.fetchDataQuery("employee", column, query);
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
		ut.adjustFont(notificationTitle, notification, minDisplay, 16);
		ut.adjustFont(attendanceTitle, attendance, minDisplay, 16);
		ut.adjustFont(logTitle, log, minDisplay, 16);
		ut.adjustFont(scheduleTitle, schedule, minDisplay, 16);
		ut.adjustFont(payrollTitle, payroll, minDisplay, 16);
		ut.adjustFont(manageTitle, manage, minDisplay, 16);
		ut.adjustFont(manageSearchCategory, manage, minDisplay, 11);
		ut.adjustFont(manageSearchField, manage, minDisplay, 11);
		ut.adjustFont(manageSearchButton, manage, minDisplay, 11);
		ut.adjustFont(manageAddButton, manage, minDisplay, 11);
		ut.adjustFont(manageUpdateButton, manage, minDisplay, 11);
		ut.adjustFont(manageDeleteButton, manage, minDisplay, 11);
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
		
		if (gl.isDark) {
			photo.setIcon(gl.darkEmployee);
		} else {
			photo.setIcon(gl.lightEmployee);
		}
	}
	
	private void toggleOne(JToggleButton enabled) {
		JToggleButton[] list = {dashboardLabel, notificationLabel, attendanceLabel, logLabel, 
				manageLabel, scheduleLabel, payrollLabel};
		for (JToggleButton b : list) {
			if (b != enabled) {
				b.setSelected(false);
				b.setEnabled(true);
			}
		}
		enabled.setEnabled(false);
	}
}
