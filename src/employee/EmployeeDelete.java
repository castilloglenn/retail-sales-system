package employee;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Utility;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class EmployeeDelete extends JDialog {
	
	private JPanel container;
	private JMenuItem themeSwitcher;
	private JLabel nameLabel;

	private Gallery gl;
	private Utility ut;
	private Database db;
	private JTextField idField;
	
	public EmployeeDelete(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Delete Employee | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(320, 240));
		setSize(320, 240);
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
		SpringLayout sl_container = new SpringLayout();
		container.setLayout(sl_container);
		
		JLabel deleteTitle = new JLabel("WARNING: THIS PROCESS IS IRREVERSIBLE.");
		deleteTitle.setHorizontalAlignment(SwingConstants.CENTER);
		deleteTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_container.putConstraint(SpringLayout.EAST, deleteTitle, -10, SpringLayout.EAST, container);
		deleteTitle.setName("");
		sl_container.putConstraint(SpringLayout.NORTH, deleteTitle, 10, SpringLayout.NORTH, container);
		sl_container.putConstraint(SpringLayout.WEST, deleteTitle, 10, SpringLayout.WEST, container);
		container.add(deleteTitle);
		
		JSeparator separator = new JSeparator();
		sl_container.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, deleteTitle);
		sl_container.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, deleteTitle);
		sl_container.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, deleteTitle);
		container.add(separator);
		
		JLabel idLabel = new JLabel("Enter the Employee ID:");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.NORTH, idLabel, 6, SpringLayout.SOUTH, separator);
		sl_container.putConstraint(SpringLayout.WEST, idLabel, 0, SpringLayout.WEST, separator);
		sl_container.putConstraint(SpringLayout.EAST, idLabel, 0, SpringLayout.EAST, separator);
		container.add(idLabel);
		
		idField = new JTextField();
		idField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sl_container.putConstraint(SpringLayout.NORTH, idField, 4, SpringLayout.SOUTH, idLabel);
		sl_container.putConstraint(SpringLayout.EAST, idField, 0, SpringLayout.EAST, idLabel);
		idField.setMargin(new Insets(2, 5, 2, 5));
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.WEST, idField, 0, SpringLayout.WEST, deleteTitle);
		container.add(idField);
		idField.setColumns(10);
		
		nameLabel = new JLabel("Name: ");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sl_container.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.WEST, idField);
		sl_container.putConstraint(SpringLayout.EAST, nameLabel, 0, SpringLayout.EAST, idField);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.NORTH, nameLabel, 6, SpringLayout.SOUTH, idField);
		container.add(nameLabel);
		
		JPanel panel = new JPanel();
		sl_container.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, nameLabel);
		sl_container.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, container);
		sl_container.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, container);
		sl_container.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, container);
		container.add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(deleteButton);
		
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(cancelButton);

		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Object[] data = db.fetchEmployeeByID(Long.parseLong(idField.getText()));
					if (data != null) {
						String name = data[1].toString() + " " + data[2].toString() + " " + data[3].toString();
						nameLabel.setText("Name: " + name);
					} else {
						nameLabel.setText("Name: ");
					}
				} catch (NumberFormatException e1) {}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (db.deleteEntry("employee", "employee_id", Long.parseLong(idField.getText()))) {
					JOptionPane.showMessageDialog(
							null, 
							  "<html>"
							+ "<p style=\"text-align: center;\">Employee has been deleted.</p>"
							+ "</html>", 
							"Success! | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		gl.getAllComponentsChangeTheme(this, 6);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
