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
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Utility;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class EmployeeNew extends JDialog {

	private JLabel title, idLabel, positionLabel, fnameLabel;
	private JTextField idField, fnameFIeld;
	private JPanel container, personal, salary;
	private JMenuItem themeSwitcher;
	
	private String[] positions = {
		"Manager", "Senior Supervisor", "Junior Supervisor", "Cashier", 
		"Inventory Clerk", "Store Assistant"
	};

	private Gallery gl;
	private Utility ut;
	private Database db;
	private JLabel mnameLabel;
	private JTextField mnameField;
	private JLabel lnameLabel;
	private JTextField lnameField;
	private JSeparator separator_1;
	private JLabel addressLabel;
	private JLabel streetLabel;
	private JTextField streetField;
	private JLabel barangayLabel;
	private JTextField barangayField;
	private JLabel cityLabel;
	private JTextField cityField;
	private JLabel provinceLabel;
	private JTextField provinceField;
	private JSeparator separator_2;
	private JLabel passLabel;
	private JPasswordField passField;
	private JLabel verifyLabel;
	private JPasswordField verifyField;
	
	public EmployeeNew(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("New Employee | " + Main.SYSTEM_NAME);
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
		
		title = new JLabel("REGISTER NEW EMPLOYEE");
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setBounds(10, 10, 276, 23);
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
		
		idField = new JTextField(
			Long.toString(
				ut.generateEmployeeID(
					db.fetchLastEmployee(), 5))
		);
		idField.setFocusable(false);
		idField.setEditable(false);
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
		
		JComboBox<String> positionComboBox = new JComboBox<String>();
		sl_personal.putConstraint(SpringLayout.NORTH, positionComboBox, -2, SpringLayout.NORTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, positionComboBox, 2, SpringLayout.SOUTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.EAST, positionComboBox, -10, SpringLayout.EAST, personal);
		positionComboBox.setModel(new DefaultComboBoxModel<String>(positions));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		positionComboBox.setRenderer(listRenderer);
		personal.add(positionComboBox);
		
		JSeparator separator = new JSeparator();
		sl_personal.putConstraint(SpringLayout.NORTH, separator, 10, SpringLayout.SOUTH, positionLabel);
		sl_personal.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, idLabel);
		sl_personal.putConstraint(SpringLayout.EAST, separator, -10, SpringLayout.EAST, personal);
		personal.add(separator);
		
		fnameLabel = new JLabel("First Name:");
		sl_personal.putConstraint(SpringLayout.NORTH, fnameLabel, 10, SpringLayout.SOUTH, separator);
		sl_personal.putConstraint(SpringLayout.WEST, fnameLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(fnameLabel);
		
		fnameFIeld = new JTextField();
		fnameFIeld.setHorizontalAlignment(SwingConstants.CENTER);
		fnameFIeld.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, fnameFIeld, -2, SpringLayout.NORTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, fnameFIeld, 2, SpringLayout.SOUTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.EAST, fnameFIeld, -10, SpringLayout.EAST, personal);
		personal.add(fnameFIeld);
		fnameFIeld.setColumns(10);
		
		mnameLabel = new JLabel("Middle Name:");
		sl_personal.putConstraint(SpringLayout.NORTH, mnameLabel, 7, SpringLayout.SOUTH, fnameLabel);
		sl_personal.putConstraint(SpringLayout.WEST, mnameLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(mnameLabel);
		
		mnameField = new JTextField();
		sl_personal.putConstraint(SpringLayout.WEST, fnameFIeld, 0, SpringLayout.WEST, mnameField);
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
		
		streetLabel = new JLabel("Street Name:");
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
		
		passLabel = new JLabel("Password:");
		sl_personal.putConstraint(SpringLayout.NORTH, passLabel, 10, SpringLayout.SOUTH, separator_2);
		sl_personal.putConstraint(SpringLayout.WEST, passLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(passLabel);
		
		passField = new JPasswordField();
		passField.setHorizontalAlignment(SwingConstants.CENTER);
		passField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, passField, -2, SpringLayout.NORTH, passLabel);
		sl_personal.putConstraint(SpringLayout.WEST, passField, 0, SpringLayout.WEST, mnameField);
		sl_personal.putConstraint(SpringLayout.SOUTH, passField, 2, SpringLayout.SOUTH, passLabel);
		sl_personal.putConstraint(SpringLayout.EAST, passField, -10, SpringLayout.EAST, personal);
		personal.add(passField);
		passField.setColumns(10);
		
		verifyLabel = new JLabel("Verify Password:");
		sl_personal.putConstraint(SpringLayout.NORTH, verifyLabel, 7, SpringLayout.SOUTH, passLabel);
		sl_personal.putConstraint(SpringLayout.WEST, verifyLabel, 0, SpringLayout.WEST, idLabel);
		personal.add(verifyLabel);
		
		verifyField = new JPasswordField();
		sl_personal.putConstraint(SpringLayout.WEST, mnameField, 0, SpringLayout.WEST, verifyField);
		verifyField.setHorizontalAlignment(SwingConstants.CENTER);
		verifyField.setMargin(new Insets(2, 5, 2, 5));
		sl_personal.putConstraint(SpringLayout.NORTH, verifyField, -2, SpringLayout.NORTH, verifyLabel);
		sl_personal.putConstraint(SpringLayout.WEST, verifyField, 6, SpringLayout.EAST, verifyLabel);
		sl_personal.putConstraint(SpringLayout.SOUTH, verifyField, 2, SpringLayout.SOUTH, verifyLabel);
		sl_personal.putConstraint(SpringLayout.EAST, verifyField, -10, SpringLayout.EAST, personal);
		personal.add(verifyField);
		verifyField.setColumns(10);
		
		salary = new JPanel();
		salary.setBounds(307, 44, 287, 320);
		container.add(salary);
		salary.setLayout(null);
		
		
		
		
		
		
		
		
		
		
		

		positionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idField.setText(
					Long.toString(
							ut.generateEmployeeID(
								db.fetchLastEmployee(), 
								5 - positionComboBox.getSelectedIndex()))
				);
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
		
		adjustTheme(false);
//		setResizable(false);
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
		salary.setBorder(new TitledBorder(null, "Salary Explanation", TitledBorder.LEADING, 
			TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
