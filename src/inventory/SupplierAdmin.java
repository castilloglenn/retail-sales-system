package inventory;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class SupplierAdmin extends JDialog {
	
	private String[] COLUMNS = {
			"Supplier ID", "Name", "Address", "Contact #"
	};
	private Object[] data;
	
	private JPanel container, manage, delivery;
	private JMenuItem themeSwitcher;
	private JLabel manageTitle;
	private JComboBox<String> comboBox;
	private JLabel manageNameLabel;
	private JLabel manageAddressLabel;
	private JLabel manageContactLabel;
	private JButton confirmButton;
	private JLabel deliveryIDLabel;
	private JTable table;
	private JLabel manageIDLabel;
	private JTextField manageIDField;
	private JTextField manageNameField;
	private JTextField manageAddressField;
	private JTextField manageContactField;
	private JTextField deliveryIDField;
	private JLabel deliveryProductLabel;
	private JTextField deliveryProductField;
	private JLabel deliveryQtyLabel;
	private JTextField textField;
	private JButton receiveButton;
	private JPanel panel;
	private JButton cancelButton;
	private JTextArea productArea;

	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public SupplierAdmin(Gallery gl, Utility ut, Database db) {
		setResizable(false);
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Manage Suppliers | " + Main.SYSTEM_NAME);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 326, 242);
		container.add(scrollPane);
		
		table = new JTable(1, 4);
		scrollPane.setViewportView(table);
		
		manage = new JPanel();
		manage.setBorder(new TitledBorder(null, "Manage Suppliers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		manage.setBounds(346, 11, 248, 242);
		container.add(manage);
		SpringLayout sl_manage = new SpringLayout();
		manage.setLayout(sl_manage);
		
		manageTitle = new JLabel("Select Operation:");
		sl_manage.putConstraint(SpringLayout.NORTH, manageTitle, 10, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, manageTitle, 10, SpringLayout.WEST, manage);
		manage.add(manageTitle);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ADD", "UPDATE", "DELETE"}));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		comboBox.setRenderer(listRenderer);
		sl_manage.putConstraint(SpringLayout.NORTH, comboBox, -2, SpringLayout.NORTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, manageTitle);
		sl_manage.putConstraint(SpringLayout.SOUTH, comboBox, 2, SpringLayout.SOUTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, comboBox, -10, SpringLayout.EAST, manage);
		manage.add(comboBox);
		
		manageIDLabel = new JLabel("Supplier ID:");
		sl_manage.putConstraint(SpringLayout.WEST, manageIDLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(manageIDLabel);
		
		manageIDField = new JTextField(Long.toString(ut.generateSupplierID(db.fetchLastEntryByTable("supplier", "supplier_id"))));
		manageIDField.setEditable(false);
		manageIDField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, manageIDField, -2, SpringLayout.NORTH, manageIDLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageIDField, 6, SpringLayout.EAST, manageIDLabel);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageIDField, 2, SpringLayout.SOUTH, manageIDLabel);
		sl_manage.putConstraint(SpringLayout.EAST, manageIDField, -10, SpringLayout.EAST, manage);
		manage.add(manageIDField);
		manageIDField.setColumns(10);
		
		manageNameLabel = new JLabel("Name:");
		sl_manage.putConstraint(SpringLayout.NORTH, manageNameLabel, 6, SpringLayout.SOUTH, manageIDLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageNameLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(manageNameLabel);
		
		manageNameField = new JTextField();
		manageNameField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, manageNameField, -2, SpringLayout.NORTH, manageNameLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageNameField, 0, SpringLayout.WEST, manageIDField);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageNameField, 2, SpringLayout.SOUTH, manageNameLabel);
		sl_manage.putConstraint(SpringLayout.EAST, manageNameField, -10, SpringLayout.EAST, manage);
		manage.add(manageNameField);
		manageNameField.setColumns(10);

		manageAddressLabel = new JLabel("Address:");
		sl_manage.putConstraint(SpringLayout.NORTH, manageAddressLabel, 6, SpringLayout.SOUTH, manageNameLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageAddressLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(manageAddressLabel);
		
		manageAddressField = new JTextField();
		manageAddressField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, manageAddressField, -2, SpringLayout.NORTH, manageAddressLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageAddressField, 0, SpringLayout.WEST, manageIDField);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageAddressField, 2, SpringLayout.SOUTH, manageAddressLabel);
		sl_manage.putConstraint(SpringLayout.EAST, manageAddressField, -10, SpringLayout.EAST, manage);
		manage.add(manageAddressField);
		manageAddressField.setColumns(10);

		manageContactLabel = new JLabel("Contact #:");
		sl_manage.putConstraint(SpringLayout.NORTH, manageContactLabel, 6, SpringLayout.SOUTH, manageAddressLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageContactLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(manageContactLabel);
		
		manageContactField = new JTextField();
		manageContactField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, manageContactField, -2, SpringLayout.NORTH, manageContactLabel);
		sl_manage.putConstraint(SpringLayout.WEST, manageContactField, 0, SpringLayout.WEST, manageIDField);
		sl_manage.putConstraint(SpringLayout.SOUTH, manageContactField, 2, SpringLayout.SOUTH, manageContactLabel);
		sl_manage.putConstraint(SpringLayout.EAST, manageContactField, -10, SpringLayout.EAST, manage);
		manage.add(manageContactField);
		manageContactField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator, 20, SpringLayout.SOUTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.NORTH, manageIDLabel, 20, SpringLayout.SOUTH, separator);
		sl_manage.putConstraint(SpringLayout.WEST, separator, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, separator, -10, SpringLayout.EAST, manage);
		manage.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator_1, 20, SpringLayout.SOUTH, manageContactLabel);
		sl_manage.putConstraint(SpringLayout.WEST, separator_1, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, separator_1, -10, SpringLayout.EAST, manage);
		manage.add(separator_1);

		confirmButton = new JButton("CONFIRM");
		sl_manage.putConstraint(SpringLayout.NORTH, confirmButton, 10, SpringLayout.SOUTH, separator_1);
		sl_manage.putConstraint(SpringLayout.WEST, confirmButton, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, confirmButton, -10, SpringLayout.EAST, manage);
		manage.add(confirmButton);
		
		delivery = new JPanel();
		delivery.setBorder(new TitledBorder(null, "Receive Delivery", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		delivery.setBounds(10, 264, 584, 146);
		container.add(delivery);
		delivery.setLayout(null);

		deliveryIDLabel = new JLabel("Supplier ID:");
		deliveryIDLabel.setBounds(16, 34, 70, 14);
		delivery.add(deliveryIDLabel);
		
		deliveryIDField = new JTextField();
		deliveryIDField.setBounds(88, 34, 241, 18);
		deliveryIDField.setHorizontalAlignment(SwingConstants.CENTER);
		delivery.add(deliveryIDField);
		deliveryIDField.setColumns(10);
		
		deliveryProductLabel = new JLabel("Product ID:");
		deliveryProductLabel.setBounds(16, 54, 70, 14);
		delivery.add(deliveryProductLabel);
		
		deliveryProductField = new JTextField();
		deliveryProductField.setBounds(88, 54, 241, 18);
		deliveryProductField.setHorizontalAlignment(SwingConstants.CENTER);
		delivery.add(deliveryProductField);
		deliveryProductField.setColumns(10);
		
		deliveryQtyLabel = new JLabel("Quantity:");
		deliveryQtyLabel.setBounds(16, 74, 70, 14);
		delivery.add(deliveryQtyLabel);
		
		textField = new JTextField();
		textField.setBounds(88, 74, 241, 18);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		delivery.add(textField);
		textField.setColumns(10);
		
		panel = new JPanel();
		panel.setBounds(88, 103, 241, 23);
		delivery.add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		
		receiveButton = new JButton("Receive");
		panel.add(receiveButton);
		
		cancelButton = new JButton("Cancel Deliver");
		panel.add(cancelButton);
		
		productArea = new JTextArea();
		productArea.setBorder(new TitledBorder(null, "Product Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		productArea.setMargin(new Insets(10, 10, 10, 10));
		productArea.setBounds(339, 34, 227, 92);
		delivery.add(productArea);
		productArea.setColumns(10);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add
				if (comboBox.getSelectedIndex() == 0) {
					manageIDField.addKeyListener(null);
					manageIDField.setText(
						Long.toString(
							ut.generateSupplierID(db.fetchLastEntryByTable("supplier", "supplier_id")
						))
					);
					manageIDField.setEditable(false);
					manageNameField.setEditable(true);
					manageAddressField.setEditable(true);
					manageContactField.setEditable(true);
					confirmButton.setEnabled(true);
				}
				
				// update
				else if (comboBox.getSelectedIndex() == 1) {
					manageIDField.setText("");
					manageNameField.setText("");
					manageAddressField.setText("");
					manageContactField.setText("");
					manageIDField.setEditable(true);
					manageNameField.setEditable(false);
					manageAddressField.setEditable(false);
					manageContactField.setEditable(false);
					confirmButton.setEnabled(false);

					manageIDField.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							try {
								data = db.fetchSupplierByID(Long.parseLong(manageIDField.getText()));
								if (data != null) {
									manageNameField.setText(data[1].toString());
									manageAddressField.setText(data[2].toString());
									manageContactField.setText(data[3].toString());
									manageNameField.setEditable(true);
									manageAddressField.setEditable(true);
									manageContactField.setEditable(true);
									confirmButton.setEnabled(true);
								} else {
									manageNameField.setText("");
									manageAddressField.setText("");
									manageContactField.setText("");
									manageNameField.setEditable(false);
									manageAddressField.setEditable(false);
									manageContactField.setEditable(false);
									confirmButton.setEnabled(false);
								}
							} catch (NumberFormatException e1) {}
						}
					});
				}
				
				// delete
				else if (comboBox.getSelectedIndex() == 2) {
					manageIDField.setText("");
					manageNameField.setText("");
					manageAddressField.setText("");
					manageContactField.setText("");
					manageIDField.setEditable(true);
					manageNameField.setEditable(false);
					manageAddressField.setEditable(false);
					manageContactField.setEditable(false);
					confirmButton.setEnabled(true);

					manageIDField.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							try {
								data = db.fetchSupplierByID(Long.parseLong(manageIDField.getText()));
								if (data != null) {
									manageNameField.setText(data[1].toString());
									manageAddressField.setText(data[2].toString());
									manageContactField.setText(data[3].toString());
									confirmButton.setEnabled(true);
								} else {
									manageNameField.setText("");
									manageAddressField.setText("");
									manageContactField.setText("");
									confirmButton.setEnabled(false);
								}
							} catch (NumberFormatException e1) {}
						}
					});
				}
				
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add
				if (comboBox.getSelectedIndex() == 0) {
					if (checkFields()) {
						if (db.insertNewSupplier(new Object[] {
							Long.parseLong(manageIDField.getText()),
							manageNameField.getText(),
							manageAddressField.getText(),
							manageContactField.getText()
						})) {
							JOptionPane.showMessageDialog(
								null, "Successfully created new supplier!", "Success | " + Main.SYSTEM_NAME, 
								JOptionPane.INFORMATION_MESSAGE);
							manageIDField.setText(
								Long.toString(
									ut.generateSupplierID(db.fetchLastEntryByTable("supplier", "supplier_id")
								))
							);
							manageNameField.setText("");
							manageAddressField.setText("");
							manageContactField.setText("");
						}
					}
				}
				
				// update
				else if (comboBox.getSelectedIndex() == 1) {
					if (checkFields()) {
						if (db.updateSupplier(new Object[] {
							Long.parseLong(manageIDField.getText()),
							manageNameField.getText(),
							manageAddressField.getText(),
							manageContactField.getText()
						})) {
							JOptionPane.showMessageDialog(
								null, "Successfully updated (" + manageNameField.getText() + ").", 
								"Success | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
							manageIDField.setText("");
							manageNameField.setText("");
							manageAddressField.setText("");
							manageContactField.setText("");
							manageNameField.setEditable(false);
							manageAddressField.setEditable(false);
							manageContactField.setEditable(false);
							confirmButton.setEnabled(false);
							
						}
					}
				}
				
				
				// delete
				else if (comboBox.getSelectedIndex() == 2) {
					int res = JOptionPane.showConfirmDialog(
						null, "Are you sure you want to delete (" + manageNameField.getText() + ")?", 
						"Please confirm | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
					if (res == 0) {
						if (db.deleteEntry("supplier", "supplier_id", Long.parseLong(manageIDField.getText()))) {
							JOptionPane.showMessageDialog(
								null, "Successfully updated (" + manageNameField.getText() + ").", 
								"Success | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
							manageIDField.setText("");
							manageIDField.setEditable(true);
							manageNameField.setEditable(false);
							manageAddressField.setEditable(false);
							manageContactField.setEditable(false);
							confirmButton.setEnabled(true);
							confirmButton.setEnabled(false);
						}
						
					}
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
				table.setModel(
					ut.generateTable(
						db.fetchDataQuery("supplier", "supplier_id", "", "supplier_id", "ASC"), COLUMNS
					)
				);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				for(int col=0; col < 4; col++){
					table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
			    };
			    
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
		gl.getAllComponentsChangeTheme(this, 7);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		manage.setBorder(
			new TitledBorder(null, "Manage Suppliers", TitledBorder.LEADING, 
				TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		delivery.setBorder(
			new TitledBorder(null, "Receive Delivery", TitledBorder.LEADING, 
				TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		productArea.setBorder(
			new TitledBorder(null, "Product Details", TitledBorder.LEADING, 
				TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT));
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
	
	private boolean checkFields() {
		String errors = "Plase check your fields:\n";
		int init = errors.length();
		
		if (manageNameField.getText().isBlank()) errors += "• Supplier's name cannot be empty.\n";
		if (manageAddressField.getText().isBlank()) errors += "• Address cannot be empty.\n";
		if (manageContactField.getText().isBlank()) errors += "• Contact number cannot be empty.\n";
		
		if (errors.length() > init) {
			JOptionPane.showMessageDialog(
				null, errors, "Check your inputs | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE);
			return false;
		} else return true;
	}
}
