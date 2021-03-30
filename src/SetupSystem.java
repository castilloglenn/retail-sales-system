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

	public SetupSystem(Gallery gl, Database db, Utility ut) {
		this.gl = gl; this.db = db; this.ut = ut;
		
		setTitle("Initial System Setup | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(640, 480);
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(gl.DFRAME_BACKGROUND);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JPanel container = new JPanel();
		container.setBackground(gl.DFRAME_BACKGROUND);
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		container.setLayout(new GridLayout(0, 2, 10, 0));
		
		form = new JPanel();
		form.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		form.setBackground(gl.DPANEL_BACKGROUND);
		container.add(form);
		SpringLayout sl_form = new SpringLayout();
		form.setLayout(sl_form);
		
		formTitle = new JLabel("BUSINESS MANAGER REGISTRATION FORM");
		formTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		formTitle.setForeground(gl.DFONT);
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
		formNotice.setForeground(gl.DFONT);
		sl_form.putConstraint(SpringLayout.EAST, formNotice, -10, SpringLayout.EAST, form);
		formNotice.setToolTipText("");
		sl_form.putConstraint(SpringLayout.NORTH, formNotice, 6, SpringLayout.SOUTH, formTitle);
		sl_form.putConstraint(SpringLayout.WEST, formNotice, 10, SpringLayout.WEST, form);
		form.add(formNotice);
		
		form2 = new JPanel();
		sl_form.putConstraint(SpringLayout.SOUTH, form2, -10, SpringLayout.SOUTH, form);
		sl_form.putConstraint(SpringLayout.EAST, form2, -10, SpringLayout.EAST, form);
		form2.setBorder(new TitledBorder(null, "Personal Information", TitledBorder.LEADING, TitledBorder.TOP, null, gl.DFONT));
		form2.setBackground(gl.DPANEL_BACKGROUND);
		sl_form.putConstraint(SpringLayout.NORTH, form2, 6, SpringLayout.SOUTH, formNotice);
		sl_form.putConstraint(SpringLayout.WEST, form2, 10, SpringLayout.WEST, form);
		form.add(form2);
		SpringLayout sl_form2 = new SpringLayout();
		form2.setLayout(sl_form2);
		
		idLabel = new JLabel("EMPLOYEE ID:");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		idLabel.setForeground(gl.DFONT);
		sl_form2.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, form2);
		sl_form2.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, form2);
		form2.add(idLabel);
		
		idField = new JTextField();
		sl_form2.putConstraint(SpringLayout.NORTH, idField, -2, SpringLayout.NORTH, idLabel);
		sl_form2.putConstraint(SpringLayout.WEST, idField, 6, SpringLayout.EAST, idLabel);
		sl_form2.putConstraint(SpringLayout.SOUTH, idField, 2, SpringLayout.SOUTH, idLabel);
		sl_form2.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, form2);
		form2.add(idField);
		idField.setColumns(10);
		
		container2 = new JPanel();
		container2.setBackground(gl.DFRAME_BACKGROUND);
		container.add(container2);
		container2.setLayout(new GridLayout(2, 0, 0, 10));

		database = new JPanel();
		database.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		database.setBackground(gl.DPANEL_BACKGROUND);
		container2.add(database);
		SpringLayout sl_database = new SpringLayout();
		database.setLayout(sl_database);
		
		databaseTitle = new JLabel("DATABASE SETUP");
		databaseTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		databaseTitle.setForeground(gl.DFONT);
		sl_database.putConstraint(SpringLayout.NORTH, databaseTitle, 10, SpringLayout.NORTH, database);
		sl_database.putConstraint(SpringLayout.WEST, databaseTitle, 10, SpringLayout.WEST, database);
		database.add(databaseTitle);
		
		databaseNotice = new JLabel(
			"<html>"
			+ "<p style=\"text-align: left;\">"
			+ "Please change the default password of MySQL Database on XAMPP, or you can use the default password."
			+ "</p>"
			+ "</html>"
		);
		databaseNotice.setFont(new Font("Tahoma", Font.PLAIN, 10));
		databaseNotice.setForeground(gl.DFONT);
		sl_database.putConstraint(SpringLayout.NORTH, databaseNotice, 6, SpringLayout.SOUTH, databaseTitle);
		sl_database.putConstraint(SpringLayout.WEST, databaseNotice, 0, SpringLayout.WEST, databaseTitle);
		sl_database.putConstraint(SpringLayout.EAST, databaseNotice, -10, SpringLayout.EAST, database);
		database.add(databaseNotice);
		
		manual = new JPanel();
		manual.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		manual.setBackground(gl.DPANEL_BACKGROUND);
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
			}
		});
		
		setVisible(true);
	}
}
