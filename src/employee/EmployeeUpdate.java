package employee;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Utility;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class EmployeeUpdate extends JDialog {

	private JLabel title, idLabel, positionLabel, fnameLabel, mnameLabel, lnameLabel, addressLabel,
		streetLabel, barangayLabel, cityLabel, provinceLabel, passLabel, verifyLabel, chooseLabel,
		grossLabel, contributionLabel, sssLabel, philhealthLabel, pagibigLabel, totalContributionLabel,
		netLabel;
	private JTextField idField, fnameField, mnameField, lnameField, streetField, barangayField,
		cityField, provinceField, grossField, sssField, philhealthField,
		pagibigField, totalContributionField, netField;
	private JSeparator separator, separator_1, separator_2, separator_3, separator_4;
	private JSpinner basicMonthlySpinner, basicDailySpinner;
	private JRadioButton dailyButton, monthlyButton;
	private JPasswordField passField, verifyField;
	private JPanel container, personal, salary;
	private JComboBox<String> positionComboBox;
	private JMenuItem themeSwitcher;
	private JCheckBox termsCheckBox;
	private JButton updateButton;
	
	private String[] positions = {
		"MANAGER", "SENIOR SUPERVISOR", "JUNIOR SUPERVISOR", "CASHIER", 
		"INVENTORY CLERK", "STORE ASSISTANT"
	};
	private double[] defaultBasic = {
		25000.0, 17500.0, 15000.0, 12500.0, 12500.0, 10000
	};
	private double MONTHLY_MIN = 5000.0;
	private double DAILY_MIN = 150.0;
	private String[] address;
	private Object[] data;

	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public EmployeeUpdate(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Update Employee | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 480));
		setSize(640, 480);
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
		
		title = new JLabel("UPDATE EXISTING EMPLOYEE");
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setBounds(10, 10, 307, 23);
		container.add(title);
		
		personal = new JPanel();
		personal.setBounds(10, 44, 287, 366);
		container.add(personal);
		SpringLayout sl_personal = new SpringLayout();
		personal.setLayout(sl_personal);
		
		idLabel = new JLabel("Employee ID:");
		sl_personal.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, personal);
		sl_personal.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, personal);
		personal.add(idLabel);
		
		idField = new JTextField();
		idField.requestFocus();
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		idField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, idField, -2, SpringLayout.NORTH, idLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, idField, 2, SpringLayout.SOUTH, idLabel);
		sl_personal.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, personal);
		personal.add(idField);
		idField.setColumns(10);
		
		positionLabel = new JLabel("Position:");
		sl_personal.putConstraint(SpringLayout.NORTH, positionLabel, 7, SpringLayout.SOUTH, idLabel);
		sl_personal.putConstraint(SpringLayout.WEST, positionLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(positionLabel);
		
		positionComboBox = new JComboBox<String>();
		sl_personal.putConstraint(SpringLayout.NORTH, positionComboBox, -2, SpringLayout.NORTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, positionComboBox, 2, SpringLayout.SOUTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.EAST, positionComboBox, -10, SpringLayout.EAST, personal);
		positionComboBox.setModel(new DefaultComboBoxModel<String>(positions));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		positionComboBox.setRenderer(listRenderer);
		personal.add(positionComboBox);
		
		separator = new JSeparator();
		sl_personal.putConstraint(SpringLayout.NORTH, separator, 10, SpringLayout.SOUTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, idLabel);
		sl_personal.putConstraint(SpringLayout.EAST, separator, -10, SpringLayout.EAST, personal);
		personal.add(separator);
		
		fnameLabel = new JLabel("First Name:");
		sl_personal.putConstraint(SpringLayout.NORTH, fnameLabel, 10, SpringLayout.SOUTH, separator);
		sl_personal.putConstraint(SpringLayout.WEST, fnameLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(fnameLabel);
		
		fnameField = new JTextField();
		fnameField.setHorizontalAlignment(SwingConstants.CENTER);
		fnameField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, fnameField, -2, SpringLayout.NORTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, fnameField, 2, SpringLayout.SOUTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.EAST, fnameField, -10, SpringLayout.EAST, personal);
		personal.add(fnameField);
		fnameField.setColumns(10);
		
		mnameLabel = new JLabel("Middle Name:");
		sl_personal.putConstraint(SpringLayout.NORTH, mnameLabel, 7, SpringLayout.SOUTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.WEST, mnameLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(mnameLabel);
		
		mnameField = new JTextField();
		sl_personal.putConstraint(SpringLayout.WEST, fnameField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.WEST, positionComboBox, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.WEST, idField, 0, SpringLayout.WEST, mnameField);
		mnameField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_personal.putConstraint(SpringLayout.NORTH, mnameField, -2, SpringLayout.NORTH, mnameLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, mnameField, 2, SpringLayout.SOUTH, mnameLabel);
		sl_personal.putConstraint(SpringLayout.EAST, mnameField, -10, SpringLayout.EAST, personal);
		personal.add(mnameField);
		mnameField.setColumns(10);
		
		lnameLabel = new JLabel("Last Name:");
		sl_personal.putConstraint(SpringLayout.NORTH, lnameLabel, 7, SpringLayout.SOUTH, mnameLabel);
		sl_personal.putConstraint(SpringLayout.WEST, lnameLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(lnameLabel);
		
		lnameField = new JTextField();
		sl_personal.putConstraint(SpringLayout.WEST, lnameField, 0, SpringLayout.WEST, mnameField);
		lnameField.setHorizontalAlignment(SwingConstants.CENTER);
		lnameField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, lnameField, -2, SpringLayout.NORTH, lnameLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, lnameField, 2, SpringLayout.SOUTH, lnameLabel);
		sl_personal.putConstraint(SpringLayout.EAST, lnameField, -10, SpringLayout.EAST, personal);
		personal.add(lnameField);
		lnameField.setColumns(10);
		
		separator_1 = new JSeparator();
		sl_personal.putConstraint(SpringLayout.NORTH, separator_1, 10, SpringLayout.SOUTH, lnameLabel);
		sl_personal.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, idLabel);
		sl_personal.putConstraint(SpringLayout.EAST, separator_1, -10, SpringLayout.EAST, personal);
		personal.add(separator_1);
		
		addressLabel = new JLabel("Home Address");
		sl_personal.putConstraint(SpringLayout.NORTH, addressLabel, 10, SpringLayout.SOUTH, separator_1);
		sl_personal.putConstraint(SpringLayout.WEST, addressLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(addressLabel);
		
		streetLabel = new JLabel("Street:");
		sl_personal.putConstraint(SpringLayout.NORTH, streetLabel, 10, SpringLayout.SOUTH, addressLabel);
		sl_personal.putConstraint(SpringLayout.WEST, streetLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(streetLabel);
		
		streetField = new JTextField();
		streetField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_personal.putConstraint(SpringLayout.NORTH, streetField, -2, SpringLayout.NORTH, streetLabel);
		sl_personal.putConstraint(SpringLayout.WEST, streetField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.SOUTH, streetField, 2, SpringLayout.SOUTH, streetLabel);
		sl_personal.putConstraint(SpringLayout.EAST, streetField, -10, SpringLayout.EAST, personal);
		streetField.setMargin(new Insets(2, 5, 2, 5));
		personal.add(streetField);
		streetField.setColumns(10);
		
		barangayLabel = new JLabel("Barangay:");
		sl_personal.putConstraint(SpringLayout.NORTH, barangayLabel, 7, SpringLayout.SOUTH, streetLabel);
		barangayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_personal.putConstraint(SpringLayout.WEST, barangayLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(barangayLabel);
		
		barangayField = new JTextField();
		barangayField.setMargin(new Insets(2, 5, 2, 5));
		barangayField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_personal.putConstraint(SpringLayout.NORTH, barangayField, -2, SpringLayout.NORTH, barangayLabel);
		sl_personal.putConstraint(SpringLayout.WEST, barangayField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.SOUTH, barangayField, 2, SpringLayout.SOUTH, barangayLabel);
		sl_personal.putConstraint(SpringLayout.EAST, barangayField, -10, SpringLayout.EAST, personal);
		personal.add(barangayField);
		barangayField.setColumns(10);
		
		cityLabel = new JLabel("City:");
		sl_personal.putConstraint(SpringLayout.NORTH, cityLabel, 7, SpringLayout.SOUTH, barangayLabel);
		sl_personal.putConstraint(SpringLayout.WEST, cityLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(cityLabel);
		
		cityField = new JTextField();
		sl_personal.putConstraint(SpringLayout.NORTH, cityField, -2, SpringLayout.NORTH, cityLabel);
		sl_personal.putConstraint(SpringLayout.WEST, cityField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.SOUTH, cityField, 2, SpringLayout.SOUTH, cityLabel);
		sl_personal.putConstraint(SpringLayout.EAST, cityField, -10, SpringLayout.EAST, personal);
		cityField.setHorizontalAlignment(SwingConstants.CENTER);
		cityField.setMargin(new Insets(2, 5, 2, 5));
		personal.add(cityField);
		cityField.setColumns(10);
		
		provinceLabel = new JLabel("Province:");
		sl_personal.putConstraint(SpringLayout.NORTH, provinceLabel, 7, SpringLayout.SOUTH, cityLabel);
		sl_personal.putConstraint(SpringLayout.WEST, provinceLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(provinceLabel);
		
		provinceField = new JTextField();
		provinceField.setHorizontalAlignment(SwingConstants.CENTER);
		provinceField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, provinceField, -2, SpringLayout.NORTH, provinceLabel);
		sl_personal.putConstraint(SpringLayout.WEST, provinceField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.SOUTH, provinceField, 2, SpringLayout.SOUTH, provinceLabel);
		sl_personal.putConstraint(SpringLayout.EAST, provinceField, -10, SpringLayout.EAST, personal);
		personal.add(provinceField);
		provinceField.setColumns(10);
		
		separator_2 = new JSeparator();
		sl_personal.putConstraint(SpringLayout.NORTH, separator_2, 10, SpringLayout.SOUTH, provinceLabel);
		sl_personal.putConstraint(SpringLayout.WEST, separator_2, 0, SpringLayout.WEST, idLabel);
		sl_personal.putConstraint(SpringLayout.EAST, separator_2, -10, SpringLayout.EAST, personal);
		personal.add(separator_2);
		
		passLabel = new JLabel("Change Password:");
		sl_personal.putConstraint(SpringLayout.NORTH, passLabel, 10, SpringLayout.SOUTH, separator_2);
		sl_personal.putConstraint(SpringLayout.WEST, passLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(passLabel);
		
		passField = new JPasswordField();
		sl_personal.putConstraint(SpringLayout.WEST, passField, 6, SpringLayout.EAST, passLabel);
		passField.setHorizontalAlignment(SwingConstants.CENTER);
		passField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, passField, -2, SpringLayout.NORTH, passLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, passField, 2, SpringLayout.SOUTH, passLabel);
		sl_personal.putConstraint(SpringLayout.EAST, passField, -10, SpringLayout.EAST, personal);
		personal.add(passField);
		passField.setColumns(10);
		
		verifyLabel = new JLabel("Verify Password:");
		sl_personal.putConstraint(SpringLayout.NORTH, verifyLabel, 7, SpringLayout.SOUTH, passLabel);
		sl_personal.putConstraint(SpringLayout.WEST, verifyLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(verifyLabel);
		
		verifyField = new JPasswordField();
		sl_personal.putConstraint(SpringLayout.WEST, verifyField, 0, SpringLayout.WEST, passField);
		sl_personal.putConstraint(SpringLayout.WEST, mnameField, 0, SpringLayout.WEST, verifyField);
		verifyField.setHorizontalAlignment(SwingConstants.CENTER);
		verifyField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, verifyField, -2, SpringLayout.NORTH, verifyLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, verifyField, 2, SpringLayout.SOUTH, verifyLabel);
		sl_personal.putConstraint(SpringLayout.EAST, verifyField, -10, SpringLayout.EAST, personal);
		personal.add(verifyField);
		verifyField.setColumns(10);
		
		salary = new JPanel();
		salary.setBounds(307, 44, 287, 295);
		container.add(salary);
		SpringLayout sl_salary = new SpringLayout();
		salary.setLayout(sl_salary);
		
		basicMonthlySpinner = new JSpinner();
		sl_salary.putConstraint(SpringLayout.EAST, basicMonthlySpinner, -10, SpringLayout.EAST, salary);
		basicMonthlySpinner.setModel(new SpinnerNumberModel(0, 0, 99999.0, 1.0));
		salary.add(basicMonthlySpinner);
		
		chooseLabel = new JLabel("Choose one:");
		salary.add(chooseLabel);
		
		monthlyButton = new JRadioButton("Basic Pay (Monthly)");
		sl_salary.putConstraint(SpringLayout.NORTH, monthlyButton, 2, SpringLayout.SOUTH, chooseLabel);
		sl_salary.putConstraint(SpringLayout.NORTH, basicMonthlySpinner, 2, SpringLayout.NORTH, monthlyButton);
		sl_salary.putConstraint(SpringLayout.WEST, basicMonthlySpinner, 6, SpringLayout.EAST, monthlyButton);
		sl_salary.putConstraint(SpringLayout.SOUTH, basicMonthlySpinner, -2, SpringLayout.SOUTH, monthlyButton);
		monthlyButton.setSelected(true);
		sl_salary.putConstraint(SpringLayout.WEST, monthlyButton, 0, SpringLayout.WEST, chooseLabel);
		ButtonGroup bg = new ButtonGroup();
		bg.add(monthlyButton);
		salary.add(monthlyButton);
		
		dailyButton = new JRadioButton("Basic Pay (Daily)");
		sl_salary.putConstraint(SpringLayout.NORTH, dailyButton, 2, SpringLayout.SOUTH, monthlyButton);
		sl_salary.putConstraint(SpringLayout.WEST, dailyButton, 0, SpringLayout.WEST, chooseLabel);
		bg.add(dailyButton);
		salary.add(dailyButton);
		
		basicDailySpinner = new JSpinner();
		basicDailySpinner.setModel(new SpinnerNumberModel(0, 0, 99999.0, 1.0));
		sl_salary.putConstraint(SpringLayout.NORTH, basicDailySpinner, 2, SpringLayout.NORTH, dailyButton);
		sl_salary.putConstraint(SpringLayout.WEST, basicDailySpinner, 0, SpringLayout.WEST, basicMonthlySpinner);
		sl_salary.putConstraint(SpringLayout.SOUTH, basicDailySpinner, -2, SpringLayout.SOUTH, dailyButton);
		sl_salary.putConstraint(SpringLayout.EAST, basicDailySpinner, -10, SpringLayout.EAST, salary);
		salary.add(basicDailySpinner);
		
		grossField = new JTextField();
		grossField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_salary.putConstraint(SpringLayout.EAST, grossField, -10, SpringLayout.EAST, salary);
		grossField.setEditable(false);
		salary.add(grossField);
		grossField.setColumns(10);
		
		grossLabel = new JLabel("Gross Basic Pay:");
		sl_salary.putConstraint(SpringLayout.NORTH, chooseLabel, 6, SpringLayout.SOUTH, grossLabel);
		sl_salary.putConstraint(SpringLayout.WEST, chooseLabel, 0, SpringLayout.WEST, grossLabel);
		sl_salary.putConstraint(SpringLayout.NORTH, grossField, -2, SpringLayout.NORTH, grossLabel);
		sl_salary.putConstraint(SpringLayout.SOUTH, grossField, 2, SpringLayout.SOUTH, grossLabel);
		sl_salary.putConstraint(SpringLayout.NORTH, grossLabel, 10, SpringLayout.NORTH, salary);
		sl_salary.putConstraint(SpringLayout.WEST, grossLabel, 10, SpringLayout.WEST, salary);
		salary.add(grossLabel);
		
		separator_3 = new JSeparator();
		sl_salary.putConstraint(SpringLayout.NORTH, separator_3, 10, SpringLayout.SOUTH, dailyButton);
		sl_salary.putConstraint(SpringLayout.WEST, separator_3, 0, SpringLayout.WEST, grossLabel);
		sl_salary.putConstraint(SpringLayout.EAST, separator_3, -10, SpringLayout.EAST, salary);
		salary.add(separator_3);
		
		contributionLabel = new JLabel("Contributions");
		sl_salary.putConstraint(SpringLayout.NORTH, contributionLabel, 6, SpringLayout.SOUTH, separator_3);
		sl_salary.putConstraint(SpringLayout.WEST, contributionLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(contributionLabel);
		
		sssLabel = new JLabel("SSS:");
		sl_salary.putConstraint(SpringLayout.NORTH, sssLabel, 6, SpringLayout.SOUTH, contributionLabel);
		sl_salary.putConstraint(SpringLayout.WEST, sssLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(sssLabel);
		
		sssField = new JTextField();
		sl_salary.putConstraint(SpringLayout.NORTH, sssField, -2, SpringLayout.NORTH, sssLabel);
		sl_salary.putConstraint(SpringLayout.SOUTH, sssField, 2, SpringLayout.SOUTH, sssLabel);
		sssField.setHorizontalAlignment(SwingConstants.CENTER);
		sssField.setMargin(new Insets(2, 5, 2, 5));
		sssField.setEditable(false);
		sl_salary.putConstraint(SpringLayout.WEST, sssField, 0, SpringLayout.WEST, grossField);
		sl_salary.putConstraint(SpringLayout.EAST, sssField, -10, SpringLayout.EAST, salary);
		salary.add(sssField);
		sssField.setColumns(10);
		
		philhealthLabel = new JLabel("PhilHealth:");
		sl_salary.putConstraint(SpringLayout.NORTH, philhealthLabel, 6, SpringLayout.SOUTH, sssLabel);
		sl_salary.putConstraint(SpringLayout.WEST, philhealthLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(philhealthLabel);
		
		philhealthField = new JTextField();
		philhealthField.setMargin(new Insets(2, 5, 2, 5));
		philhealthField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_salary.putConstraint(SpringLayout.NORTH, philhealthField, -2, SpringLayout.NORTH, philhealthLabel);
		sl_salary.putConstraint(SpringLayout.WEST, philhealthField, 0, SpringLayout.WEST, grossField);
		sl_salary.putConstraint(SpringLayout.SOUTH, philhealthField, 2, SpringLayout.SOUTH, philhealthLabel);
		sl_salary.putConstraint(SpringLayout.EAST, philhealthField, -10, SpringLayout.EAST, salary);
		philhealthField.setEditable(false);
		salary.add(philhealthField);
		philhealthField.setColumns(10);
		
		pagibigLabel = new JLabel("Pag-Ibig:");
		sl_salary.putConstraint(SpringLayout.NORTH, pagibigLabel, 6, SpringLayout.SOUTH, philhealthLabel);
		sl_salary.putConstraint(SpringLayout.WEST, pagibigLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(pagibigLabel);
		
		pagibigField = new JTextField();
		sl_salary.putConstraint(SpringLayout.NORTH, pagibigField, -2, SpringLayout.NORTH, pagibigLabel);
		sl_salary.putConstraint(SpringLayout.SOUTH, pagibigField, 2, SpringLayout.SOUTH, pagibigLabel);
		sl_salary.putConstraint(SpringLayout.EAST, pagibigField, -10, SpringLayout.EAST, salary);
		pagibigField.setHorizontalAlignment(SwingConstants.CENTER);
		pagibigField.setEditable(false);
		sl_salary.putConstraint(SpringLayout.WEST, pagibigField, 0, SpringLayout.WEST, grossField);
		salary.add(pagibigField);
		pagibigField.setColumns(10);
		
		separator_4 = new JSeparator();
		sl_salary.putConstraint(SpringLayout.NORTH, separator_4, 10, SpringLayout.SOUTH, pagibigLabel);
		sl_salary.putConstraint(SpringLayout.WEST, separator_4, 0, SpringLayout.WEST, grossLabel);
		sl_salary.putConstraint(SpringLayout.EAST, separator_4, -10, SpringLayout.EAST, salary);
		salary.add(separator_4);
		
		totalContributionLabel = new JLabel("Total Contributions:");
		sl_salary.putConstraint(SpringLayout.NORTH, totalContributionLabel, 10, SpringLayout.SOUTH, separator_4);
		sl_salary.putConstraint(SpringLayout.WEST, totalContributionLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(totalContributionLabel);
		
		totalContributionField = new JTextField();
		sl_salary.putConstraint(SpringLayout.WEST, grossField, 0, SpringLayout.WEST, totalContributionField);
		totalContributionField.setMargin(new Insets(2, 5, 2, 5));
		totalContributionField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_salary.putConstraint(SpringLayout.NORTH, totalContributionField, -2, SpringLayout.NORTH, totalContributionLabel);
		sl_salary.putConstraint(SpringLayout.SOUTH, totalContributionField, 2, SpringLayout.SOUTH, totalContributionLabel);
		totalContributionField.setEditable(false);
		sl_salary.putConstraint(SpringLayout.WEST, totalContributionField, 6, SpringLayout.EAST, totalContributionLabel);
		sl_salary.putConstraint(SpringLayout.EAST, totalContributionField, -10, SpringLayout.EAST, salary);
		salary.add(totalContributionField);
		totalContributionField.setColumns(10);
		
		netLabel = new JLabel("Net Basic Pay:");
		sl_salary.putConstraint(SpringLayout.NORTH, netLabel, 6, SpringLayout.SOUTH, totalContributionLabel);
		sl_salary.putConstraint(SpringLayout.WEST, netLabel, 0, SpringLayout.WEST, grossLabel);
		salary.add(netLabel);
		
		netField = new JTextField();
		netField.setMargin(new Insets(2, 5, 2, 5));
		netField.setHorizontalAlignment(SwingConstants.CENTER);
		netField.setEditable(false);
		sl_salary.putConstraint(SpringLayout.NORTH, netField, -2, SpringLayout.NORTH, netLabel);
		sl_salary.putConstraint(SpringLayout.WEST, netField, 0, SpringLayout.WEST, totalContributionField);
		sl_salary.putConstraint(SpringLayout.SOUTH, netField, 2, SpringLayout.SOUTH, netLabel);
		sl_salary.putConstraint(SpringLayout.EAST, netField, -10, SpringLayout.EAST, salary);
		salary.add(netField);
		netField.setColumns(10);
		
		termsCheckBox = new JCheckBox(
			"<html><i>I hereby certify that all information stated above is true and correct.</i></html>"
		);
		termsCheckBox.setBounds(303, 346, 287, 31);
		container.add(termsCheckBox);
		
		updateButton = new JButton("UPDATE EMPLOYEE DETAIL");
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		updateButton.setBounds(307, 384, 287, 23);
		container.add(updateButton);
		

		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					data = db.fetchEmployeeByID(Long.parseLong(idField.getText()));
					if (data != null) {
						populateFields();
					} else {
						closeFields();
					}
				} catch (NumberFormatException e1) {
					closeFields();
				}
			}
		});
		positionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grossField.setText(
					String.format(
						"Php %,.2f", defaultBasic[positionComboBox.getSelectedIndex()]
					)
				);
				basicMonthlySpinner.setValue(defaultBasic[positionComboBox.getSelectedIndex()]);
				basicDailySpinner.setValue(((double) basicMonthlySpinner.getValue()) / 30);
				updateSalaryFields();
				checkChanged();
			}
		});
		fnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		mnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		lnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		streetField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		barangayField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		cityField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		provinceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkChanged();
			}
		});
		monthlyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basicMonthlySpinner.setEnabled(true);
				basicDailySpinner.setEnabled(false);
			}
		});
		dailyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basicDailySpinner.setEnabled(true);
				basicMonthlySpinner.setEnabled(false);
			}
		});
		basicMonthlySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				grossField.setText(
					String.format("Php %,.2f", (double) basicMonthlySpinner.getValue())
				);
				updateSalaryFields();
				checkChanged();
			}
		});
		basicDailySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				basicMonthlySpinner.setValue(((double) basicDailySpinner.getValue()) * 30);
				grossField.setText(
					String.format("Php %,.2f", ((double) basicDailySpinner.getValue()) * 30)
				);
				updateSalaryFields();
				checkChanged();
			}
		});
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
					insertRecord();
				}
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
				adjustTheme(false);
			}
		});
		
		closeFields();
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
		personal.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, 
			TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		salary.setBorder(new TitledBorder(null, "Salary", TitledBorder.LEADING, 
			TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
	
	private void updateSalaryFields() {
		double grossPay = (double) basicMonthlySpinner.getValue();
		grossField.setText(
			String.format("Php %,.2f", (double) basicMonthlySpinner.getValue())
		);
		sssField.setText(
			String.format("Php %,.2f", ((double) basicMonthlySpinner.getValue()) * db.SSS_RATE)
			);
		philhealthField.setText(
			String.format("Php %,.2f", ((double) basicMonthlySpinner.getValue()) * db.PHILHEALTH_RATE)
		);
		pagibigField.setText(String.format("Php %,.2f", db.PAGIBIG_RATE));
		totalContributionField.setText(
			String.format("Php %,.2f", grossPay * 
				(db.SSS_RATE + db.PHILHEALTH_RATE) + db.PAGIBIG_RATE)
		);
		netField.setText(
			String.format("Php %,.2f", grossPay - 
				(grossPay * (db.SSS_RATE + db.PHILHEALTH_RATE) + db.PAGIBIG_RATE))
		);
	}
	
	private void clearSalaryFields() {
		grossField.setText(String.format("Php %,.2f", 0.0));
		basicMonthlySpinner.setModel(new SpinnerNumberModel(MONTHLY_MIN, MONTHLY_MIN, 99999.0, 1.0));
		basicDailySpinner.setModel(new SpinnerNumberModel(DAILY_MIN, DAILY_MIN, 99999.0, 1.0));
		sssField.setText(String.format("Php %,.2f", 0.0));
		philhealthField.setText(String.format("Php %,.2f", 0.0));
		pagibigField.setText(String.format("Php %,.2f", 0.0));
		totalContributionField.setText(String.format("Php %,.2f", 0.0));
		netField.setText(String.format("Php %,.2f", 0.0));
		adjustTheme(false);
	}
	
	private boolean checkFields() {
		String[] errors = new String[5];
		String pass = new String(passField.getPassword());
		String vpass = new String(verifyField.getPassword());
		boolean flagged = false;
		
		if (fnameField.getText().isBlank()) errors[0] = "• First name field cannot be empty.\n";
		if (lnameField.getText().isBlank()) errors[1] = "• Last name field cannot be empty.\n";
		if (streetField.getText().isBlank() ||
			barangayField.getText().isBlank() ||
			cityField.getText().isBlank() ||
			provinceField.getText().isBlank()) errors[2] = "• One of the address field cannot be empty.\n";
		
		if (!pass.isBlank()) {
			if (pass.length() < 6 || pass.length() > 15) {
				errors[3] = "• New password must be 6 to 15 characters in length.\n";
			} else if (!pass.equals(vpass)) {
				errors[3] = "• Passwords doesn't match.\n";
			}
		}

		if (!termsCheckBox.isSelected()) errors[4] = "• You must certify about the informations provided.";

		String message = "Please check your inputs:\n";
		for (String err : errors) {
			if (err != null) {
				flagged = true;
				message += err;
			}
		}

		if (flagged) {
			JOptionPane.showMessageDialog(
				null, message, "Invalid input | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE
			);
			return false;
		} else {
			return true;
		}
	}
	
	private void checkChanged() {
		if (data != null && address != null) {
			if ((data[0] != null && !positionComboBox.getSelectedItem().toString().equals(data[0].toString())) ||
				(data[1] != null && !fnameField.getText().equals(data[1].toString())) ||
				(data[2] != null && !mnameField.getText().equals(data[2].toString())) ||
				(data[3] != null && !lnameField.getText().equals(data[3].toString())) ||
				(address[0] != null && !streetField.getText().equals(address[0])) ||
				(address[1] != null && !barangayField.getText().equals(address[1])) ||
				(address[2] != null && !cityField.getText().equals(address[2])) ||
				(address[3] != null && !provinceField.getText().equals(address[3])) ||
				(data[5] != null && 
					Double.parseDouble(
						basicMonthlySpinner.getValue().toString()) != 
						Double.parseDouble(data[5].toString())) ||
				(!new String(passField.getPassword()).isBlank())
			) {
				termsCheckBox.setEnabled(true);
				updateButton.setEnabled(true);
			} else {
				termsCheckBox.setEnabled(false);
				updateButton.setEnabled(false);
			}
		}
	}
	
	private void populateFields() {
		address = collapseAddress(data[4].toString());
		
		positionComboBox.setEnabled(true);
		positionComboBox.setSelectedItem(data[0]);
		
		grossField.setText(
			String.format(
				"Php %,.2f", Double.parseDouble(data[5].toString())
			)
		);
		basicMonthlySpinner.setEnabled(true);
		basicMonthlySpinner.setModel(new SpinnerNumberModel(
			Double.parseDouble(data[5].toString()), MONTHLY_MIN, 99999.0, 1.0)
		);
		basicDailySpinner.setModel(new SpinnerNumberModel(
			Double.parseDouble(data[5].toString()) / 30, DAILY_MIN, 99999.0, 1.0));
		
		fnameField.setEnabled(true);
		fnameField.setText(data[1].toString());
		mnameField.setEnabled(true);
		mnameField.setText(data[2].toString());
		lnameField.setEnabled(true);
		lnameField.setText(data[3].toString());
		
		streetField.setEnabled(true);
		streetField.setText(address[0]);
		barangayField.setEnabled(true);
		barangayField.setText(address[1]);
		cityField.setEnabled(true);
		cityField.setText(address[2]);
		provinceField.setEnabled(true);
		provinceField.setText(address[3]);
		
		passField.setEnabled(true);
		verifyField.setEnabled(true);
		
		monthlyButton.setEnabled(true);
		dailyButton.setEnabled(true);
		grossField.setEnabled(true);
		sssField.setEnabled(true);
		philhealthField.setEnabled(true);
		pagibigField.setEnabled(true);
		totalContributionField.setEnabled(true);
		netField.setEnabled(true);

		termsCheckBox.setEnabled(false);
		updateButton.setEnabled(false);
		
		adjustTheme(false);
	}
	
	private void closeFields() {
		positionComboBox.setEnabled(false);
		positionComboBox.setSelectedIndex(0);
		
		fnameField.setEnabled(false);
		fnameField.setText("");
		mnameField.setEnabled(false);
		mnameField.setText("");
		lnameField.setEnabled(false);
		lnameField.setText("");
		
		streetField.setEnabled(false);
		streetField.setText("");
		verifyField.setEnabled(false);
		verifyField.setText("");
		barangayField.setEnabled(false);
		barangayField.setText("");
		cityField.setEnabled(false);
		cityField.setText("");
		provinceField.setEnabled(false);
		provinceField.setText("");
		
		passField.setEnabled(false);
		passField.setText("");
		verifyField.setEnabled(false);
		verifyField.setText("");
		
		basicMonthlySpinner.setEnabled(false);
		monthlyButton.setEnabled(false);
		dailyButton.setEnabled(false);
		basicDailySpinner.setEnabled(false);
		grossField.setEnabled(false);
		sssField.setEnabled(false);
		philhealthField.setEnabled(false);
		pagibigField.setEnabled(false);
		totalContributionField.setEnabled(false);
		netField.setEnabled(false);
		
		termsCheckBox.setEnabled(false);
		updateButton.setEnabled(false);
		
		clearSalaryFields();
	}
	
	private String[] collapseAddress(String address) {
		String[] collapse = new String[4];
		String part = "";
		int index = 0;
		
		for (char c : address.toCharArray()) {
			if (c == ',') {
				collapse[index] = part.replace(',', ' ').strip();
				if (index < 3) part = "";
				index++;
			}
			part += c;
		}
		collapse[index] = part.replace(',', ' ').strip();
		return collapse;
	}
	
	private void insertRecord() {
		StringBuilder address = new StringBuilder();
		address.append(streetField.getText() + ", ");
		address.append(barangayField.getText() + ", ");
		address.append(cityField.getText() + ", ");
		address.append(provinceField.getText());
		
		Object[] data = {
				Long.parseLong(idField.getText()),
				positions[positionComboBox.getSelectedIndex()].toUpperCase(),
				fnameField.getText().toUpperCase(),
				(mnameField.getText().isBlank()) ? null : mnameField.getText().toUpperCase(),
				lnameField.getText().toUpperCase(),
				address.toString().toUpperCase(),
				(double) basicMonthlySpinner.getValue(),
				(new String(passField.getPassword()).isBlank()) ? null : ut.hashData(new String(passField.getPassword()))
		};
		
		if (db.updateExistingEmployee(data)) {
			JOptionPane.showMessageDialog(
					null, 
					  "<html>"
					+ "<p style=\"text-align: center;\">Employee has been updated.</p>"
					+ "</html>", 
					"Success! | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
}
