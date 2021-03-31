import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import utils.Database;
import utils.Gallery;
import utils.Utility;

import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SetupSystem extends JFrame {

	private JPanel container, contentPane, form, container2, database, manual, form2, database2, submit;
	private SpringLayout sl_form, sl_contentPane;
	private JLabel formTitle, databaseTitle, databaseNotice, formNotice, idLabel, positionLabel, 
				fnameLabel, mnameLabel, lnameLabel, addressLabel, payLabel, terms, termsNotice,
				userLabel, passLabel;
	private JTextField idField, positionField, fnameField, mnameField, lnameField, addressField,
				userField, passField;
	private JCheckBox submitCheckBox, defaultCheckBox;
	private JTextArea termsField;
	private JSpinner paySpinner;
	private JScrollPane scrollPane;
	private JButton submitButton;
	private JMenuItem themeSwitcher;
	
	private Gallery gl;
	private Database db;
	private Utility ut;

	public SetupSystem(Gallery gl, Database db, Utility ut) {
		this.gl = gl; this.db = db; this.ut = ut;
		
		setTitle("Initial System Setup | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(640, 480);
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		addPopup(this, popupMenu);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		container = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		container.setLayout(new GridLayout(0, 2, 10, 0));
		
		form = new JPanel();
		form.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		container.add(form);
		
		sl_form = new SpringLayout();
		form.setLayout(sl_form);
		
		formTitle = new JLabel("BUSINESS MANAGER REGISTRATION FORM");
		formTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_form.putConstraint(SpringLayout.NORTH, formTitle, 10, SpringLayout.NORTH, form);
		sl_form.putConstraint(SpringLayout.WEST, formTitle, 10, SpringLayout.WEST, form);
		form.add(formTitle);
		
		formNotice = new JLabel(
			"<html>"
			+ "<p style=\"text-align: left\">"
			+ "Please enter your data as the system requires an initial user to control the system."
			+ "</p>"
			+ "</html>");
		formNotice.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sl_form.putConstraint(SpringLayout.EAST, formNotice, -10, SpringLayout.EAST, form);
		formNotice.setToolTipText("");
		sl_form.putConstraint(SpringLayout.NORTH, formNotice, 6, SpringLayout.SOUTH, formTitle);
		sl_form.putConstraint(SpringLayout.WEST, formNotice, 10, SpringLayout.WEST, form);
		form.add(formNotice);
		
		form2 = new JPanel();
		sl_form.putConstraint(SpringLayout.EAST, form2, -10, SpringLayout.EAST, form);
		form2.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, TitledBorder.TOP, null));
		sl_form.putConstraint(SpringLayout.NORTH, form2, 6, SpringLayout.SOUTH, formNotice);
		sl_form.putConstraint(SpringLayout.WEST, form2, 10, SpringLayout.WEST, form);
		form.add(form2);
		SpringLayout sl_form2 = new SpringLayout();
		form2.setLayout(sl_form2);
		
		idLabel = new JLabel("Employee ID:");
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, form2);
		sl_form2.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, form2);
		form2.add(idLabel);
		
		idField = new JTextField(Long.toString(ut.generateEmployeeID(db.fetchLastIDByTable("employee", "employee_id"), 5)));
		idField.setMargin(new Insets(2, 5, 2, 5));
		idField.setEditable(false);
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_form2.putConstraint(SpringLayout.NORTH, idField, -2, SpringLayout.NORTH, idLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, idField, 2, SpringLayout.SOUTH, idLabel);
		sl_form2.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, form2);
		form2.add(idField);
		idField.setColumns(10);
		
		positionLabel = new JLabel("Position:");
		positionLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, positionLabel, 6, SpringLayout.SOUTH, idLabel);
		sl_form2.putConstraint(SpringLayout.WEST, positionLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(positionLabel);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		
		fnameLabel = new JLabel("First Name:");
		sl_form2.putConstraint(SpringLayout.NORTH, fnameLabel, 18, SpringLayout.SOUTH, positionLabel);
		fnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.WEST, fnameLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(fnameLabel);
		
		positionField = new JTextField();
		positionField.setMargin(new Insets(2, 5, 2, 5));
		positionField.setHorizontalAlignment(SwingConstants.CENTER);
		positionField.setText("MANAGER");
		positionField.setEditable(false);
		sl_form2.putConstraint(SpringLayout.NORTH, positionField, -2, SpringLayout.NORTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, positionField, 2, SpringLayout.SOUTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.EAST, positionField, -10, SpringLayout.EAST, form2);
		form2.add(positionField);
		positionField.setColumns(10);
		
		fnameField = new JTextField();
		fnameField.setMargin(new Insets(2, 5, 2, 5));
		fnameField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_form2.putConstraint(SpringLayout.NORTH, fnameField, -2, SpringLayout.NORTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, fnameField, 2, SpringLayout.SOUTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.EAST, fnameField, -10, SpringLayout.EAST, form2);
		form2.add(fnameField);
		fnameField.setColumns(10);
		
		mnameLabel = new JLabel("Middle Name:");
		mnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, mnameLabel, 6, SpringLayout.SOUTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, mnameLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(mnameLabel);
		
		mnameField = new JTextField();
		sl_form2.putConstraint(SpringLayout.WEST, fnameField, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.WEST, positionField, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.WEST, idField, 0, SpringLayout.WEST, mnameField);
		mnameField.setHorizontalAlignment(SwingConstants.CENTER);
		mnameField.setMargin(new Insets(2, 5, 2, 5));
		sl_form2.putConstraint(SpringLayout.NORTH, mnameField, -2, SpringLayout.NORTH, mnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, mnameField, 6, SpringLayout.EAST, mnameLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, mnameField, 2, SpringLayout.SOUTH, mnameLabel);
		sl_form2.putConstraint(SpringLayout.EAST, mnameField, -10, SpringLayout.EAST, form2);
		form2.add(mnameField);
		mnameField.setColumns(10);
		
		lnameLabel = new JLabel("Last Name:");
		lnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, lnameLabel, 6, SpringLayout.SOUTH, mnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, lnameLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(lnameLabel);
		
		lnameField = new JTextField();
		lnameField.setHorizontalAlignment(SwingConstants.CENTER);
		lnameField.setMargin(new Insets(2, 5, 2, 5));
		sl_form2.putConstraint(SpringLayout.NORTH, lnameField, -2, SpringLayout.NORTH, lnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, lnameField, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.SOUTH, lnameField, 2, SpringLayout.SOUTH, lnameLabel);
		sl_form2.putConstraint(SpringLayout.EAST, lnameField, -10, SpringLayout.EAST, form2);
		form2.add(lnameField);
		lnameField.setColumns(10);
		
		addressLabel = new JLabel("Full Address:");
		sl_form2.putConstraint(SpringLayout.NORTH, addressLabel, 6, SpringLayout.SOUTH, lnameLabel);
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.WEST, addressLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(addressLabel);
		
		addressField = new JTextField();
		addressField.setHorizontalAlignment(SwingConstants.CENTER);
		addressField.setMargin(new Insets(2, 5, 2, 5));
		sl_form2.putConstraint(SpringLayout.NORTH, addressField, -2, SpringLayout.NORTH, addressLabel);
		sl_form2.putConstraint(SpringLayout.WEST, addressField, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.SOUTH, addressField, 2, SpringLayout.SOUTH, addressLabel);
		sl_form2.putConstraint(SpringLayout.EAST, addressField, -10, SpringLayout.EAST, form2);
		form2.add(addressField);
		addressField.setColumns(10);
		
		payLabel = new JLabel("Basic Pay:");
		payLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, payLabel, 6, SpringLayout.SOUTH, addressLabel);
		sl_form2.putConstraint(SpringLayout.WEST, payLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(payLabel);

		paySpinner = new JSpinner();
		paySpinner.setModel(new SpinnerNumberModel(1000.0, 1000.0, 99999.0, 1.0));
		JComponent editor = paySpinner.getEditor();
		JSpinner.DefaultEditor de = (JSpinner.DefaultEditor) editor;
		de.getTextField().setHorizontalAlignment(JTextField.CENTER);
		paySpinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, paySpinner, -2, SpringLayout.NORTH, payLabel);
		sl_form2.putConstraint(SpringLayout.WEST, paySpinner, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.SOUTH, paySpinner, 2, SpringLayout.SOUTH, payLabel);
		sl_form2.putConstraint(SpringLayout.EAST, paySpinner, -10, SpringLayout.EAST, form2);
		form2.add(paySpinner);
		
		submit = new JPanel();
		sl_form.putConstraint(SpringLayout.NORTH, submit, -75, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.SOUTH, form2, -6, SpringLayout.NORTH, submit);
		sl_form.putConstraint(SpringLayout.WEST, submit, 10, SpringLayout.WEST, form);
		sl_form.putConstraint(SpringLayout.SOUTH, submit, -10, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.EAST, submit, -10, SpringLayout.EAST, form);
		form.add(submit);
		submit.setLayout(new GridLayout(2, 0, 0, 5));
		
		submitCheckBox = new JCheckBox("I agree to the \"Terms and Conditions\"");
		submitCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		submit.add(submitCheckBox);
		
		submitButton = new JButton("SUBMIT AND START THE SYSTEM");
		submitButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		submitButton.setBackground(gl.COMP_BACKGROUND);
		submit.add(submitButton);
		
		container2 = new JPanel();
		container.add(container2);
		container2.setLayout(new GridLayout(2, 0, 0, 10));

		database = new JPanel();
		database.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		container2.add(database);
		SpringLayout sl_database = new SpringLayout();
		database.setLayout(sl_database);
		
		databaseTitle = new JLabel("DATABASE SETUP");
		databaseTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_database.putConstraint(SpringLayout.NORTH, databaseTitle, 10, SpringLayout.NORTH, database);
		sl_database.putConstraint(SpringLayout.WEST, databaseTitle, 10, SpringLayout.WEST, database);
		database.add(databaseTitle);
		
		databaseNotice = new JLabel(
			"<html><p style=\"text-align: left;\">First, change the default password of MySQL Database on XAMPP then write here the new login credentials or you can use the default.</p></html>"
		);
		databaseNotice.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sl_database.putConstraint(SpringLayout.NORTH, databaseNotice, 6, SpringLayout.SOUTH, databaseTitle);
		sl_database.putConstraint(SpringLayout.WEST, databaseNotice, 0, SpringLayout.WEST, databaseTitle);
		sl_database.putConstraint(SpringLayout.EAST, databaseNotice, -10, SpringLayout.EAST, database);
		database.add(databaseNotice);
		
		database2 = new JPanel();
		database2.setBorder(new TitledBorder(null, "Database Login Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_database.putConstraint(SpringLayout.NORTH, database2, 6, SpringLayout.SOUTH, databaseNotice);
		sl_database.putConstraint(SpringLayout.WEST, database2, 10, SpringLayout.WEST, database);
		sl_database.putConstraint(SpringLayout.SOUTH, database2, -10, SpringLayout.SOUTH, database);
		sl_database.putConstraint(SpringLayout.EAST, database2, -10, SpringLayout.EAST, database);
		database.add(database2);
		SpringLayout sl_database2 = new SpringLayout();
		database2.setLayout(sl_database2);
		
		userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_database2.putConstraint(SpringLayout.NORTH, userLabel, 10, SpringLayout.NORTH, database2);
		sl_database2.putConstraint(SpringLayout.WEST, userLabel, 10, SpringLayout.WEST, database2);
		database2.add(userLabel);
		
		userField = new JTextField();
		userField.setEditable(false);
		userField.setText("root");
		userField.setHorizontalAlignment(SwingConstants.CENTER);
		userField.setMargin(new Insets(2, 5, 2, 5));
		sl_database2.putConstraint(SpringLayout.NORTH, userField, -2, SpringLayout.NORTH, userLabel);
		sl_database2.putConstraint(SpringLayout.WEST, userField, 6, SpringLayout.EAST, userLabel);
		sl_database2.putConstraint(SpringLayout.SOUTH, userField, 2, SpringLayout.SOUTH, userLabel);
		sl_database2.putConstraint(SpringLayout.EAST, userField, -10, SpringLayout.EAST, database2);
		database2.add(userField);
		userField.setColumns(10);
		
		passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_database2.putConstraint(SpringLayout.NORTH, passLabel, 6, SpringLayout.SOUTH, userLabel);
		sl_database2.putConstraint(SpringLayout.WEST, passLabel, 0, SpringLayout.WEST, userLabel);
		database2.add(passLabel);
		
		passField = new JTextField();
		passField.setHorizontalAlignment(SwingConstants.CENTER);
		passField.setMargin(new Insets(2, 5, 2, 5));
		sl_database2.putConstraint(SpringLayout.NORTH, passField, -2, SpringLayout.NORTH, passLabel);
		sl_database2.putConstraint(SpringLayout.SOUTH, passField, 2, SpringLayout.SOUTH, passLabel);
		sl_database2.putConstraint(SpringLayout.EAST, passField, -10, SpringLayout.EAST, database2);
		passField.setEditable(false);
		sl_database2.putConstraint(SpringLayout.WEST, passField, 0, SpringLayout.WEST, userField);
		database2.add(passField);
		passField.setColumns(10);

		defaultCheckBox = new JCheckBox("Use default login");
		defaultCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		defaultCheckBox.setSelected(true);
		sl_database2.putConstraint(SpringLayout.NORTH, defaultCheckBox, 6, SpringLayout.SOUTH, passLabel);
		sl_database2.putConstraint(SpringLayout.WEST, defaultCheckBox, 0, SpringLayout.WEST, userLabel);
		database2.add(defaultCheckBox);
		
		manual = new JPanel();
		manual.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		container2.add(manual);
		SpringLayout sl_manual = new SpringLayout();
		manual.setLayout(sl_manual);
		
		terms = new JLabel("TERMS AND CONDITION");
		terms.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_manual.putConstraint(SpringLayout.NORTH, terms, 10, SpringLayout.NORTH, manual);
		sl_manual.putConstraint(SpringLayout.WEST, terms, 10, SpringLayout.WEST, manual);
		manual.add(terms);
		
		termsNotice = new JLabel("<html><p style=\"text-align: left;\">Please read the terms and conditions before using the software.</p></html>");
		termsNotice.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sl_manual.putConstraint(SpringLayout.NORTH, termsNotice, 6, SpringLayout.SOUTH, terms);
		sl_manual.putConstraint(SpringLayout.WEST, termsNotice, 0, SpringLayout.WEST, terms);
		sl_manual.putConstraint(SpringLayout.EAST, termsNotice, -10, SpringLayout.EAST, manual);
		manual.add(termsNotice);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_manual.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, termsNotice);
		sl_manual.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, manual);
		sl_manual.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, manual);
		sl_manual.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, manual);
		manual.add(scrollPane);
		
		termsField = new JTextArea(ut.getTermsAndConditions());
		termsField.setMargin(new Insets(10, 10, 10, 10));
		termsField.setLineWrap(true);
		termsField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		termsField.setEditable(false);
		scrollPane.setViewportView(termsField);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkFields();
			}
		});
		defaultCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (defaultCheckBox.isSelected()) {
					userField.setText("root");
					userField.setEditable(false);
					passField.setText("");
					passField.setEditable(false);
				} else {
					userField.setText("");
					userField.setEditable(true);
					passField.setText("");
					passField.setEditable(true);
				}
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
				adjustTheme();
			}
		});

		adjustTheme();
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
	
	private void adjustFonts() {
		int standard = 297;
		int insideStandard = 273;
		
		ut.adjustFont(formTitle, form, standard, 12);
		ut.adjustFont(formNotice, form, standard, 10);
		ut.adjustFont(databaseTitle, database, standard, 12);
		ut.adjustFont(databaseNotice, database, standard, 10);
		ut.adjustFont(terms, manual, standard, 12);
		ut.adjustFont(termsNotice, manual, standard, 10);
		ut.adjustFont(idLabel, form2, insideStandard, 11);
		ut.adjustFont(idField, form2, insideStandard, 11);
		ut.adjustFont(positionLabel, form2, insideStandard, 11);
		ut.adjustFont(positionField, form2, insideStandard, 11);
		ut.adjustFont(fnameLabel, form2, insideStandard, 11);
		ut.adjustFont(fnameField, form2, insideStandard, 11);
		ut.adjustFont(mnameLabel, form2, insideStandard, 11);
		ut.adjustFont(mnameField, form2, insideStandard, 11);
		ut.adjustFont(lnameLabel, form2, insideStandard, 11);
		ut.adjustFont(lnameField, form2, insideStandard, 11);
		ut.adjustFont(addressLabel, form2, insideStandard, 11);
		ut.adjustFont(addressField, form2, insideStandard, 11);
		ut.adjustFont(payLabel, form2, insideStandard, 11);
		ut.adjustFont(paySpinner, form2, insideStandard, 11);
		ut.adjustFont(userLabel, form2, insideStandard, 11);
		ut.adjustFont(userField, form2, insideStandard, 11);
		ut.adjustFont(passLabel, form2, insideStandard, 11);
		ut.adjustFont(passField, form2, insideStandard, 11);
		ut.adjustFont(defaultCheckBox, database2, insideStandard, 10);
		ut.adjustFont(submitCheckBox, submit, insideStandard, 11);
		ut.adjustFont(submitButton, submit, insideStandard, 11);
	}
	
	private void adjustContainer() {
		int maxWidth = 1000;
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
	
	private void adjustTheme() {
		gl.adjustTheme(new JComponent[] {form, formTitle, database, databaseTitle, manual, databaseNotice, 
				formNotice, form2, idLabel, positionLabel, fnameLabel, mnameLabel, lnameLabel, addressLabel, 
				payLabel, terms, termsNotice, database2, userLabel, passLabel, submit, submitCheckBox, defaultCheckBox});
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			contentPane.setBackground(gl.DFRAME_BACKGROUND);
			container.setBackground(gl.DFRAME_BACKGROUND);
			container2.setBackground(gl.DFRAME_BACKGROUND);
			form2.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, TitledBorder.TOP, null, gl.DFONT));
			database2.setBorder(new TitledBorder(null, "Database Login Information", TitledBorder.LEADING, TitledBorder.TOP, null, gl.DFONT));
			gl.isDark = false;
		} else {
			contentPane.setBackground(gl.LFRAME_BACKGROUND);
			container.setBackground(gl.LFRAME_BACKGROUND);
			container2.setBackground(gl.LFRAME_BACKGROUND);
			form2.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, TitledBorder.TOP, null, gl.LFONT));
			database2.setBorder(new TitledBorder(null, "Database Login Information", TitledBorder.LEADING, TitledBorder.TOP, null, gl.LFONT));
			gl.isDark = true;
		}
	}
	
	private void checkFields() {
		
	}
}