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
		sl_form.putConstraint(SpringLayout.SOUTH, form2, -10, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.EAST, form2, -10, SpringLayout.EAST, form);
		form2.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, TitledBorder.TOP, null));
		sl_form.putConstraint(SpringLayout.NORTH, form2, 6, SpringLayout.SOUTH, formNotice);
		sl_form.putConstraint(SpringLayout.WEST, form2, 10, SpringLayout.WEST, form);
		form.add(form2);
		SpringLayout sl_form2 = new SpringLayout();
		form2.setLayout(sl_form2);
		
		idLabel = new JLabel("EMPLOYEE ID:");
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, form2);
		sl_form2.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, form2);
		form2.add(idLabel);
		
		idField = new JTextField();
		idField.setMargin(new Insets(2, 5, 2, 5));
		idField.setEditable(false);
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_form2.putConstraint(SpringLayout.NORTH, idField, -2, SpringLayout.NORTH, idLabel);
		sl_form2.putConstraint(SpringLayout.WEST, idField, 6, SpringLayout.EAST, idLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, idField, 2, SpringLayout.SOUTH, idLabel);
		sl_form2.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, form2);
		form2.add(idField);
		idField.setColumns(10);
		
		positionLabel = new JLabel("POSITION:");
		positionLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, positionLabel, 6, SpringLayout.SOUTH, idLabel);
		sl_form2.putConstraint(SpringLayout.WEST, positionLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(positionLabel);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		
		fnameLabel = new JLabel("FIRST NAME:");
		fnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, fnameLabel, 6, SpringLayout.SOUTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.WEST, fnameLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(fnameLabel);
		
		positionField = new JTextField();
		positionField.setMargin(new Insets(2, 5, 2, 5));
		positionField.setHorizontalAlignment(SwingConstants.RIGHT);
		positionField.setText("MANAGER");
		positionField.setEditable(false);
		sl_form2.putConstraint(SpringLayout.NORTH, positionField, -2, SpringLayout.NORTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.WEST, positionField, 6, SpringLayout.EAST, positionLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, positionField, 2, SpringLayout.SOUTH, positionLabel);
		sl_form2.putConstraint(SpringLayout.EAST, positionField, -10, SpringLayout.EAST, form2);
		form2.add(positionField);
		positionField.setColumns(10);
		
		fnameField = new JTextField();
		fnameField.setMargin(new Insets(2, 5, 2, 5));
		fnameField.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_form2.putConstraint(SpringLayout.NORTH, fnameField, -2, SpringLayout.NORTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, fnameField, 6, SpringLayout.EAST, fnameLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, fnameField, 2, SpringLayout.SOUTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.EAST, fnameField, -10, SpringLayout.EAST, form2);
		form2.add(fnameField);
		fnameField.setColumns(10);
		
		mnameLabel = new JLabel("MIDDLE NAME:");
		mnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sl_form2.putConstraint(SpringLayout.NORTH, mnameLabel, 6, SpringLayout.SOUTH, fnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, mnameLabel, 0, SpringLayout.WEST, idLabel);
		form2.add(mnameLabel);
		
		mnameField = new JTextField();
		mnameField.setHorizontalAlignment(SwingConstants.RIGHT);
		mnameField.setMargin(new Insets(2, 5, 2, 5));
		sl_form2.putConstraint(SpringLayout.NORTH, mnameField, -2, SpringLayout.NORTH, mnameLabel);
		sl_form2.putConstraint(SpringLayout.WEST, mnameField, 6, SpringLayout.EAST, mnameLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, mnameField, 2, SpringLayout.SOUTH, mnameLabel);
		sl_form2.putConstraint(SpringLayout.EAST, mnameField, -10, SpringLayout.EAST, form2);
		form2.add(mnameField);
		mnameField.setColumns(10);
		
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
		manual.setLayout(new SpringLayout());
		
		
		
		
		
		
		

		
		
		

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double formMinWidth = 297;
				double form2MinWidth = 273;
				
				ut.adjustFont(formTitle, form, formMinWidth, 12);
				ut.adjustFont(databaseTitle, database, formMinWidth, 12);
				ut.adjustFont(formNotice, form, formMinWidth, 10);
				ut.adjustFont(databaseNotice, database, formMinWidth, 10);
				ut.adjustFont(idLabel, form2, form2MinWidth, 11);
				ut.adjustFont(idField, form2, form2MinWidth, 11);
				ut.adjustFont(positionLabel, form2, form2MinWidth, 11);
				ut.adjustFont(positionField, form2, form2MinWidth, 11);
				ut.adjustFont(fnameLabel, form2, form2MinWidth, 11);
				ut.adjustFont(fnameField, form2, form2MinWidth, 11);
				ut.adjustFont(mnameLabel, form2, form2MinWidth, 11);
				ut.adjustFont(mnameField, form2, form2MinWidth, 11);
			}
		});
		
		setVisible(true);
	}
}
