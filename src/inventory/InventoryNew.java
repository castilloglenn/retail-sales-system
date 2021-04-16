package inventory;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Utility;

@SuppressWarnings("serial")
public class InventoryNew extends JDialog {

	private String[] categories;
	
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

	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public InventoryNew(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		categories = db.fetchProductCategories();
		
		setTitle("New Product | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(400, 435);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("NEW PRODUCT");
		title.setBounds(10, 20, 158, 25);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(title);
		
		JLabel productIDLabel = new JLabel("PRODUCT ID:");
		productIDLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		productIDLabel.setBounds(10, 66, 104, 17);
		panel.add(productIDLabel);
		
		productIDField = new JTextField(Long.toString(ut.generateProductID(db.fetchLastIDByTable("product", "product_id"))));
		productIDField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productIDField.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDField.setEditable(false);
		productIDField.setBounds(124, 65, 230, 17);
		panel.add(productIDField);
		productIDField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 344, 2);
		panel.add(separator);
		
		JLabel category = new JLabel("CATEGORY:");
		category.setHorizontalAlignment(SwingConstants.TRAILING);
		category.setFont(new Font("Tahoma", Font.BOLD, 12));
		category.setBounds(10, 94, 104, 17);
		panel.add(category);
		
		categoryNew = new JTextField();
		categoryNew.setEditable(false);
		categoryNew.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryNew.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryNew.setBounds(142, 121, 212, 17);
		panel.add(categoryNew);
		categoryNew.setColumns(10);
		
		existingCategory = new JComboBox<String>();
		existingCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
		existingCategory.setBounds(124, 93, 230, 17);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		existingCategory.setRenderer(listRenderer);
		panel.add(existingCategory);
		
		JLabel quantity = new JLabel("QUANTITY:");
		quantity.setHorizontalAlignment(SwingConstants.TRAILING);
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 150, 104, 17);
		panel.add(quantity);
		
		quantityAmount = new JSpinner();
		quantityAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		quantityAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		quantityAmount.setBounds(124, 150, 230, 17);
		panel.add(quantityAmount);
		
		JLabel uomLabel = new JLabel(
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
		uomField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		uomField.setHorizontalAlignment(SwingConstants.TRAILING);
		uomField.setBounds(124, 178, 230, 17);
		panel.add(uomField);
		uomField.setColumns(10);
		
		JLabel name = new JLabel("PRODUCT NAME:");
		name.setHorizontalAlignment(SwingConstants.TRAILING);
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(10, 206, 104, 17);
		panel.add(name);
		
		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.TRAILING);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameField.setBounds(124, 206, 230, 17);
		panel.add(nameField);
		nameField.setColumns(10);
		
		JLabel purchaseValue = new JLabel(
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
		purchaseAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		purchaseAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		purchaseAmount.setBounds(124, 233, 230, 18);
		panel.add(purchaseAmount);
		
		JLabel sellValue = new JLabel("SELL VALUE:");
		sellValue.setHorizontalAlignment(SwingConstants.TRAILING);
		sellValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sellValue.setBounds(10, 262, 104, 17);
		panel.add(sellValue);
		
		sellAmount = new JSpinner();
		sellAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sellAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		sellAmount.setBounds(124, 261, 230, 18);
		panel.add(sellAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 311, 344, 49);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 10, 0));
		
		JButton insertClearButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "ADD NEW PRODUCT AND CLEAR ALL FIELDS"
				+ "</p>"
				+ "</html>"
		);
		insertClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) clearFields();
			}
		});
		insertClearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(insertClearButton);
		
		JButton insertExitButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "ADD NEW PRODUCT AND CLOSE WINDOW"
				+ "</p>"
				+ "</html>");
		insertExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) dispose();
			}
		});
		insertExitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(insertExitButton);
		
		JLabel newCategoryLabel = new JLabel(
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
			try {
				db.insertNewProduct(
					Long.parseLong(data[0].toString()),
					data[1].toString(),
					Double.parseDouble(data[2].toString()),
					data[3].toString(),
					data[4].toString(),
					Double.parseDouble(data[5].toString()),
					Double.parseDouble(data[6].toString())
				);
				JOptionPane.showMessageDialog(null, "New Product Added! (" + nameField.getText() + ")");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Product (" + nameField.getText() + ") already exists!");
			}
			return true;
		} else if (data.length == 2) {
			JOptionPane.showMessageDialog(null,
				String.format("Please check your inputs:\n"
						+ "%s%s",
						(data[0] == null) ? data[1].toString() : data[0],
						(data[0] == null) ? "" : "\n" + data[1])
			);
		}
		return false;
	}
	
	private void clearFields() {
		productIDField.setText(Long.toString(ut.generateProductID(db.fetchLastIDByTable("product", "product_id"))));
		if (categoryCheckBox.isSelected()) {
			categoryNew.setText("");
		} else {
			categories = db.fetchProductCategories();
			existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
			existingCategory.setSelectedIndex(0);
		}
		quantityAmount.setValue(1);
		uomField.setText("");
		nameField.setText("");
		purchaseAmount.setValue(1);
		sellAmount.setValue(1);
	}
}
