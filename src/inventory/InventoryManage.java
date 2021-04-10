package inventory;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Main;
import utils.Database;
import utils.Gallery;

@SuppressWarnings("serial")
public class InventoryManage extends JDialog {

	private String[] columnUpdate = {
			"category", "quantity", "uom", "name", 
			"purchase_value", "sell_value"
		};
	private String[] categories;
	private Object[] data;
	private int indexStorage;
	
	private JMenuItem themeSwitcher;
	private JTextField productIDField;
	private JTextField categoryNew;
	private JTextField uomField;
	private JTextField nameField;
	private JCheckBox categoryCheckBox;
	private JComboBox<String> existingCategory;
	private JSpinner quantityAmount;
	private JSpinner purchaseAmount;
	private JSpinner sellAmount;
	private JPanel panel;
	private JLabel title;
	private JLabel productIDLabel;
	private JLabel category;
	private JLabel quantity;
	private JLabel uomLabel;
	private JLabel name;
	private JLabel purchaseValue;
	private JLabel sellValue;
	private JButton updateButton;
	private JButton deleteButton;
	private JLabel newCategoryLabel;

	private Gallery gl;
	private Database db;
	
	public InventoryManage(Gallery gl, Database db) {
		this.gl = gl; this.db = db;
		
		categories = db.fetchProductCategories();
		
		setTitle("Manage Product | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(400, 435);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		themeSwitcher = new JMenuItem("Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		
		panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		title = new JLabel("MANAGE PRODUCT");
		title.setBounds(10, 20, 192, 25);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(title);
		
		productIDLabel = new JLabel("PRODUCT ID:");
		productIDLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		productIDLabel.setBounds(10, 66, 104, 17);
		panel.add(productIDLabel);
		
		productIDField = new JTextField("Enter the product ID here");
		productIDField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productIDField.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDField.setBounds(124, 65, 230, 17);
		panel.add(productIDField);
		productIDField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 344, 2);
		panel.add(separator);
		
		category = new JLabel("CATEGORY:");
		category.setHorizontalAlignment(SwingConstants.TRAILING);
		category.setFont(new Font("Tahoma", Font.BOLD, 12));
		category.setBounds(10, 94, 104, 17);
		panel.add(category);
		
		categoryNew = new JTextField();
		categoryNew.setEnabled(false);
		categoryNew.setEditable(false);
		categoryNew.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryNew.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryNew.setBounds(142, 121, 212, 17);
		panel.add(categoryNew);
		categoryNew.setColumns(10);
		
		existingCategory = new JComboBox<String>();
		existingCategory.setEnabled(false);
		existingCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
		existingCategory.setBounds(124, 93, 230, 17);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		existingCategory.setRenderer(listRenderer);
		panel.add(existingCategory);
		
		quantity = new JLabel("QUANTITY:");
		quantity.setHorizontalAlignment(SwingConstants.TRAILING);
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 150, 104, 17);
		panel.add(quantity);
		
		quantityAmount = new JSpinner();
		quantityAmount.setEnabled(false);
		quantityAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		quantityAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		quantityAmount.setBounds(124, 150, 230, 17);
		panel.add(quantityAmount);
		
		uomLabel = new JLabel(
			"<html>"
			+ "<p style=\"text-align:right;\">"
			+ "UNIT OF MEASUREMENT:"
			+ "</p>"
			+ "</html>"
		);
		uomLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		uomLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		uomLabel.setBounds(10, 174, 104, 26);
		panel.add(uomLabel);
		
		uomField = new JTextField();
		uomField.setEnabled(false);
		uomField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		uomField.setHorizontalAlignment(SwingConstants.TRAILING);
		uomField.setBounds(124, 178, 230, 17);
		panel.add(uomField);
		uomField.setColumns(10);
		
		name = new JLabel("PRODUCT NAME:");
		name.setHorizontalAlignment(SwingConstants.TRAILING);
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(10, 206, 104, 17);
		panel.add(name);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setHorizontalAlignment(SwingConstants.TRAILING);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameField.setBounds(124, 206, 230, 17);
		panel.add(nameField);
		nameField.setColumns(10);
		
		purchaseValue = new JLabel(
				"<html>"
				+ "<p style=\"text-align:right;\">"
				+ "PURCHASE "
				+ "VALUE:"
				+ "</p>"
				+ "</html>"
			);
		purchaseValue.setHorizontalAlignment(SwingConstants.TRAILING);
		purchaseValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		purchaseValue.setBounds(10, 229, 104, 28);
		panel.add(purchaseValue);
		
		purchaseAmount = new JSpinner();
		purchaseAmount.setEnabled(false);
		purchaseAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		purchaseAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		purchaseAmount.setBounds(124, 233, 230, 18);
		panel.add(purchaseAmount);
		
		sellValue = new JLabel("SELL VALUE:");
		sellValue.setHorizontalAlignment(SwingConstants.TRAILING);
		sellValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sellValue.setBounds(10, 262, 104, 17);
		panel.add(sellValue);
		
		sellAmount = new JSpinner();
		sellAmount.setEnabled(false);
		sellAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sellAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		sellAmount.setBounds(124, 261, 230, 18);
		panel.add(sellAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 311, 344, 49);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 10, 0));
		
		updateButton = new JButton("<html><p style=\"text-align:center;\">UPDATE PRODUCT</p></html>");
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(updateButton);
		
		deleteButton = new JButton("<html><p style=\"text-align:center;\">DELETE PRODUCT</p></html>");
		deleteButton.setEnabled(false);
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(deleteButton);
		
		newCategoryLabel = new JLabel(
				"<html>"
				+ "<p style=\"text-align:right;\">"
				+ "OR ADD NEW CATEGORY:"
				+ "</p>"
				+ "</html>");
		newCategoryLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		newCategoryLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		newCategoryLabel.setBounds(10, 118, 104, 27);
		panel.add(newCategoryLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 298, 344, 2);
		panel.add(separator_1);
		
		categoryCheckBox = new JCheckBox("");
		categoryCheckBox.setEnabled(false);
		categoryCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (categoryCheckBox.isSelected()) {
					categoryNew.setEditable(true);
					existingCategory.setSelectedIndex(-1);
					existingCategory.setEnabled(false);
				}
				if (!categoryCheckBox.isSelected()) {
					existingCategory.setEnabled(true);
					existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchProductCategories()));
					existingCategory.setSelectedIndex(0);
					categoryNew.setText("");
					categoryNew.setEditable(false);
				}
			}
		});
		categoryCheckBox.setBounds(120, 121, 21, 17);
		panel.add(categoryCheckBox);
		
		
		
		productIDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (productIDField.getText().equals("Enter the product ID here")) {
					productIDField.setText("");
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (productIDField.getText().isBlank()) {
					productIDField.setText("Enter the product ID here");
				}
				try {
					data = db.fetchProductByID(Long.parseLong(productIDField.getText()));
					if (data != null) {
						enableAndSetFields();
					} else {
						clearAndDisableFields();
					}
				} catch (NumberFormatException e2) {
					clearAndDisableFields();
				}
			}
		});
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) {
					productIDField.setText("Enter the product ID here");
					clearAndDisableFields();
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to delete (" + nameField.getText() + ")?", 
						"Confirmation", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE
				);
				
				if (confirmation == 0) {
					if (db.deleteEntry("product", "product_id", Long.parseLong(productIDField.getText()))) {
						JOptionPane.showMessageDialog(null, 
								"Product #" + productIDField.getText() + " has been deleted.\n"
										+ "Product Name: " + nameField.getText());
						productIDField.setText("Enter the product ID here");
						clearAndDisableFields();
					}
				}
			}
		});
		existingCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSwitch();
			}});
		categoryNew.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		quantityAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		uomField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		purchaseAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		sellAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		categoryCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (categoryCheckBox.isSelected()) {
					categoryNew.setEditable(true);
					existingCategory.setSelectedIndex(-1);
					existingCategory.setEnabled(false);
				} else if (!categoryCheckBox.isSelected()) {
					existingCategory.setEnabled(true);
					existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchProductCategories()));
					existingCategory.setSelectedIndex(indexStorage);
					categoryNew.setText("");
					categoryNew.setEditable(false);
				}
				updateSwitch();
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
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
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
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
	
	private void clearAndDisableFields() {
		existingCategory.setSelectedIndex(-1); existingCategory.setEnabled(false);
		categoryNew.setText(""); categoryNew.setEnabled(false);
		quantityAmount.setValue(1); quantityAmount.setEnabled(false);
		uomField.setText(""); uomField.setEnabled(false);
		nameField.setText(""); nameField.setEnabled(false);
		purchaseAmount.setValue(1); purchaseAmount.setEnabled(false);
		sellAmount.setValue(1); sellAmount.setEnabled(false);
		
		categoryCheckBox.setSelected(false);
		categoryCheckBox.setEnabled(false);
		updateButton.setEnabled(false);
		deleteButton.setEnabled(false);
	}
	
	private void enableAndSetFields() {
		for (int index = 0; index < categories.length; index++) {
			if (data[1].toString().equals(categories[index])) {
				existingCategory.setSelectedIndex(index);
				indexStorage = index;
			}
		}
		
		quantityAmount.setValue(data[2]); 
		uomField.setText(data[3].toString()); 
		nameField.setText(data[4].toString()); 
		purchaseAmount.setValue(data[5]); 
		sellAmount.setValue(data[6]); 
		
		existingCategory.setEnabled(true); categoryCheckBox.setEnabled(true);
		categoryNew.setEnabled(true); quantityAmount.setEnabled(true);
		uomField.setEnabled(true); nameField.setEnabled(true);
		purchaseAmount.setEnabled(true); sellAmount.setEnabled(true);
		deleteButton.setEnabled(true);
	}
	
	private void updateSwitch() {
		try {
			boolean categorySelector = (existingCategory.getSelectedItem() == null) 
				? categoryNew.getText().equals(data[1])
				: existingCategory.getSelectedItem().toString().equals(data[1]);
			
			if (categorySelector &&
				quantityAmount.getValue().toString().equals(data[2].toString()) &&
				uomField.getText().equals(data[3]) &&
				nameField.getText().equals(data[4]) &&
				purchaseAmount.getValue().toString().equals(data[5].toString()) &&
				sellAmount.getValue().toString().equals(data[6].toString())
			) {
				updateButton.setEnabled(false);
			} else {
				updateButton.setEnabled(true);
			}
		} catch (NullPointerException e) {
			// The system will not handle null pointers because this is also key-sensitive
		}
	}
	
	private Object[] getAllFields() {
		Object[] data = new Object[7];
		Object[] errors = new Object[2];
		boolean flagged = false;
		
		data[0] = productIDField.getText();
		if (categoryCheckBox.isSelected()) {
			String input = categoryNew.getText();
			if (input.isBlank()) {
				flagged = true;
				errors[0] = "• Your new category cannot be empty.";
			} else {
				data[1] = input;
				for (String category : categories) {
					if (input.toLowerCase().equals(category.toLowerCase())) {
						flagged = true;
						errors[0] = "• Your new category already exists.";
					}
				}
			}
		} else if (!categoryCheckBox.isSelected()) {
			String category = existingCategory.getSelectedItem().toString();
			if (category.equals("--No existing record--")) {
				flagged = true;
				errors[0] = "• Please enter a new category.";
			} else {
				data[1] = category;
			}
		}
		data[2] = quantityAmount.getValue().toString();
		String uom = uomField.getText();
		String name = nameField.getText();
		if (uom.isBlank()) {
			data[3] = "piece(s)";
		} else {
			data[3] = uom;
		}
		if (name.isBlank()) {
			flagged = true;
			errors[1] = "• Product name cannot be empty.";
		}
		data[4] = name;
		
		data[5] = purchaseAmount.getValue();
		data[6] = sellAmount.getValue();
			
		return (flagged) ? errors : data;
	}
	
	private boolean evaluateFields() {
		Object[] data = getAllFields();
		if (data.length == 7) {
			Object[] inputs = {
				(existingCategory.getSelectedItem() == null) 
					? categoryNew.getText()
					: existingCategory.getSelectedItem().toString(),
				Double.parseDouble(quantityAmount.getValue().toString()),
				uomField.getText(),
				nameField.getText(),
				Double.parseDouble(purchaseAmount.getValue().toString()),
				Double.parseDouble(sellAmount.getValue().toString())
			};
			db.updateProduct(Long.parseLong(productIDField.getText()), columnUpdate, inputs);
			JOptionPane.showMessageDialog(null, 
					"Product #" + productIDField.getText() + " has been updated.\n"
					+ "Product Name: " + nameField.getText()
			);
			return true;
		} else if (data.length == 2) {
			JOptionPane.showMessageDialog(null,
				String.format("Please check your inputs:\n"
						+ "%s%s",
						(data[0] == null) ? data[1] : data[0],
						(data[1] == null) ? "" : "\n" + data[1]
						)
			);
		}
		return false;
	}
}
