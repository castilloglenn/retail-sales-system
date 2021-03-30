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
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SetupSystem extends JFrame {

	private JPanel contentPane;
	private JPanel form;
	private JLabel formTitle;
	private JPanel container2;
	private JPanel database;
	private JLabel databaseTitle;
	private JPanel manual;
	
	private Gallery gl;
	private Database db;
	private Utility ut;
	private JLabel databaseNotice;
	private JLabel formNotice;
	private JPanel form2;
	private JLabel idLabel;
	private JTextField idField;
	private JLabel positionLabel;
	private JLabel fnameLabel;
	private JTextField positionField;
	private JTextField fnameField;
	private JLabel mnameLabel;
	private JTextField mnameField;
	private JLabel lnameLabel;
	private JTextField lnameField;
	private JLabel addressLabel;
	private JTextField addressField;
	private JLabel payLabel;
	private JLabel terms;
	private JLabel termsNotice;

	public SetupSystem(Gallery gl, Database db, Utility ut) {
		this.gl = gl; this.db = db; this.ut = ut;
		
		setTitle("Initial System Setup | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(640, 480);
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JPanel container = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		container.setLayout(new GridLayout(0, 2, 10, 0));
		
		form = new JPanel();
		form.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		container.add(form);
		SpringLayout sl_form = new SpringLayout();
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
		
		idField = new JTextField();
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
		positionField.setHorizontalAlignment(SwingConstants.RIGHT);
		positionField.setText("MANAGER");
		positionField.setEditable(false);
		sl_form2.putConstraint(SpringLayout.NORTH, positionField, -2, SpringLayout.NORTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, positionField, 2, SpringLayout.SOUTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.EAST, positionField, -10, SpringLayout.EAST, form2);
		form2.add(positionField);
		positionField.setColumns(10);
		
		fnameField = new JTextField();
		fnameField.setMargin(new Insets(2, 5, 2, 5));
		fnameField.setHorizontalAlignment(SwingConstants.RIGHT);
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
		mnameField.setHorizontalAlignment(SwingConstants.RIGHT);
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
		lnameField.setHorizontalAlignment(SwingConstants.RIGHT);
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
		addressField.setHorizontalAlignment(SwingConstants.RIGHT);
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
		
		JSpinner paySpinner = new JSpinner();
		paySpinner.setModel(new SpinnerNumberModel(1000.0, 1000.0, 99999.0, 1.0));
		paySpinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, paySpinner, -2, SpringLayout.NORTH, payLabel);
		sl_form2.putConstraint(SpringLayout.WEST, paySpinner, 0, SpringLayout.WEST, mnameField);
		sl_form2.putConstraint(SpringLayout.SOUTH, paySpinner, 2, SpringLayout.SOUTH, payLabel);
		sl_form2.putConstraint(SpringLayout.EAST, paySpinner, -10, SpringLayout.EAST, form2);
		form2.add(paySpinner);
		
		JPanel submit = new JPanel();
		sl_form.putConstraint(SpringLayout.SOUTH, form2, -6, SpringLayout.NORTH, submit);
		sl_form.putConstraint(SpringLayout.NORTH, submit, -50, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.WEST, submit, 10, SpringLayout.WEST, form);
		sl_form.putConstraint(SpringLayout.SOUTH, submit, -10, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.EAST, submit, -10, SpringLayout.EAST, form);
		form.add(submit);
		
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
		
		termsNotice = new JLabel("<html><p style=\"text-align: left;\">Please read the terms and conditions when using the software.</p></html>");
		termsNotice.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sl_manual.putConstraint(SpringLayout.NORTH, termsNotice, 6, SpringLayout.SOUTH, terms);
		sl_manual.putConstraint(SpringLayout.WEST, termsNotice, 0, SpringLayout.WEST, terms);
		sl_manual.putConstraint(SpringLayout.EAST, termsNotice, -10, SpringLayout.EAST, manual);
		manual.add(termsNotice);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_manual.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, termsNotice);
		sl_manual.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, manual);
		sl_manual.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, manual);
		sl_manual.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, manual);
		manual.add(scrollPane);
		
		JTextArea termsField = new JTextArea(ut.getTermsAndConditions());
		termsField.setMargin(new Insets(10, 10, 10, 10));
		termsField.setLineWrap(true);
		termsField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		termsField.setEditable(false);
		scrollPane.setViewportView(termsField);
		
		
		
		
		
		
		

		
		
		

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double standard = 297;
				double insideStandard = 273;
				
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
			}
		});
		
		setVisible(true);
	}
}
