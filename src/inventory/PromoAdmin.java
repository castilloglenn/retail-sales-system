package inventory;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Utility;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComponent;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class PromoAdmin extends JDialog {

	private JTextField productIDField;
	private JTextField productNameField;
	private JTextField promoTitleField;
	private JTextField conditionField;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel manage;
	private JLabel productIDLabel;
	private JLabel productNameLabel;
	private JLabel promoTitleLabel;
	private JLabel conditionLabel;
	private JLabel discountLabel;
	private JSpinner discountSpinner;
	private JLabel startLabel;
	private JSpinner startSpinner;
	private JLabel endLabel;
	private JSpinner endSpinner;
	private JButton confirmButton;
	private JPanel container;
	private JMenuItem themeSwitcher;

	private String startFinal, endFinal;
	private Gallery gl;
	private Database db;
	
	public PromoAdmin(Gallery gl, Utility ut, Database db, Logger log, long id) {
		setResizable(false);
		this.gl = gl; this.db = db;  
		
		setTitle("Manage Promos | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(700, 330);
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
		
		panel = new JPanel();
		panel.setBounds(10, 11, 652, 250);
		container.add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 0));

		scrollPane = new JScrollPane();
		panel.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		scrollPane.setViewportView(textArea);

		manage = new JPanel();
		panel.add(manage);
		SpringLayout sl_manage = new SpringLayout();
		manage.setLayout(sl_manage);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		productIDLabel = new JLabel("Product ID:");
		sl_manage.putConstraint(SpringLayout.NORTH, productIDLabel, 10, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, productIDLabel, 10, SpringLayout.WEST, manage);
		productIDLabel.setFocusable(false);
		manage.add(productIDLabel);
		
		productIDField = new JTextField();
		productIDField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, productIDField, -2, SpringLayout.NORTH, productIDLabel);
		sl_manage.putConstraint(SpringLayout.SOUTH, productIDField, 2, SpringLayout.SOUTH, productIDLabel);
		sl_manage.putConstraint(SpringLayout.EAST, productIDField, -10, SpringLayout.EAST, manage);
		manage.add(productIDField);
		productIDField.setColumns(10);

		productNameLabel = new JLabel("Product Name:");
		sl_manage.putConstraint(SpringLayout.WEST, productNameLabel, 10, SpringLayout.WEST, manage);
		productNameLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, productNameLabel, 10, SpringLayout.SOUTH, productIDLabel);
		manage.add(productNameLabel);
		
		productNameField = new JTextField();
		productNameField.setFocusable(false);
		productNameField.setEditable(false);
		productNameField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.WEST, productIDField, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.NORTH, productNameField, -2, SpringLayout.NORTH, productNameLabel);
		sl_manage.putConstraint(SpringLayout.WEST, productNameField, 6, SpringLayout.EAST, productNameLabel);
		sl_manage.putConstraint(SpringLayout.SOUTH, productNameField, 2, SpringLayout.SOUTH, productNameLabel);
		sl_manage.putConstraint(SpringLayout.EAST, productNameField, -10, SpringLayout.EAST, manage);
		manage.add(productNameField);
		productNameField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator_1, 10, SpringLayout.SOUTH, productNameLabel);
		sl_manage.putConstraint(SpringLayout.WEST, separator_1, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, separator_1, -10, SpringLayout.EAST, manage);
		manage.add(separator_1);

		promoTitleLabel = new JLabel("Promo Title:");
		sl_manage.putConstraint(SpringLayout.WEST, promoTitleLabel, 10, SpringLayout.WEST, manage);
		promoTitleLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, promoTitleLabel, 10, SpringLayout.SOUTH, separator_1);
		manage.add(promoTitleLabel);
		
		promoTitleField = new JTextField();
		promoTitleField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, promoTitleField, -2, SpringLayout.NORTH, promoTitleLabel);
		sl_manage.putConstraint(SpringLayout.WEST, promoTitleField, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, promoTitleField, 2, SpringLayout.SOUTH, promoTitleLabel);
		sl_manage.putConstraint(SpringLayout.EAST, promoTitleField, -10, SpringLayout.EAST, manage);
		manage.add(promoTitleField);
		promoTitleField.setColumns(10);

		conditionLabel = new JLabel("Condition:");
		sl_manage.putConstraint(SpringLayout.WEST, conditionLabel, 10, SpringLayout.WEST, manage);
		conditionLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, conditionLabel, 10, SpringLayout.SOUTH, promoTitleLabel);
		manage.add(conditionLabel);
		
		conditionField = new JTextField();
		conditionField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, conditionField, -2, SpringLayout.NORTH, conditionLabel);
		sl_manage.putConstraint(SpringLayout.WEST, conditionField, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, conditionField, 2, SpringLayout.SOUTH, conditionLabel);
		sl_manage.putConstraint(SpringLayout.EAST, conditionField, -10, SpringLayout.EAST, manage);
		manage.add(conditionField);
		conditionField.setColumns(10);

		discountLabel = new JLabel("Discount:");
		sl_manage.putConstraint(SpringLayout.WEST, discountLabel, 10, SpringLayout.WEST, manage);
		discountLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, discountLabel, 10, SpringLayout.SOUTH, conditionLabel);
		manage.add(discountLabel);

		discountSpinner = new JSpinner();
		discountSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 99999.0, 1.0));
		JComponent de_dateSpinner3 = discountSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor3 = (JSpinner.DefaultEditor)de_dateSpinner3;
		spinnerEditor3.getTextField().setHorizontalAlignment(JTextField.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, discountSpinner, -2, SpringLayout.NORTH, discountLabel);
		sl_manage.putConstraint(SpringLayout.WEST, discountSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, discountSpinner, 2, SpringLayout.SOUTH, discountLabel);
		sl_manage.putConstraint(SpringLayout.EAST, discountSpinner, -10, SpringLayout.EAST, manage);
		manage.add(discountSpinner);

		startLabel = new JLabel("Start:");
		sl_manage.putConstraint(SpringLayout.WEST, startLabel, 10, SpringLayout.WEST, manage);
		startLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, startLabel, 10, SpringLayout.SOUTH, discountLabel);
		manage.add(startLabel);

		startSpinner = new JSpinner();
		startSpinner.setModel(new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.DAY_OF_YEAR));
		SimpleDateFormat model = new SimpleDateFormat("MM/dd/yyyy");
		startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, model.toPattern()));
		JComponent de_dateSpinner = startSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)de_dateSpinner;
		spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, startSpinner, -2, SpringLayout.NORTH, startLabel);
		sl_manage.putConstraint(SpringLayout.WEST, startSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, startSpinner, 2, SpringLayout.SOUTH, startLabel);
		sl_manage.putConstraint(SpringLayout.EAST, startSpinner, -10, SpringLayout.EAST, manage);
		manage.add(startSpinner);

		endLabel = new JLabel("End:");
		sl_manage.putConstraint(SpringLayout.WEST, endLabel, 10, SpringLayout.WEST, manage);
		endLabel.setFocusable(false);
		sl_manage.putConstraint(SpringLayout.NORTH, endLabel, 10, SpringLayout.SOUTH, startLabel);
		manage.add(endLabel);

		endSpinner = new JSpinner();
		sl_manage.putConstraint(SpringLayout.NORTH, endSpinner, -2, SpringLayout.NORTH, endLabel);
		sl_manage.putConstraint(SpringLayout.WEST, endSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, endSpinner, 2, SpringLayout.SOUTH, endLabel);
		sl_manage.putConstraint(SpringLayout.EAST, endSpinner, -10, SpringLayout.EAST, manage);
		endSpinner.setModel(new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.DAY_OF_YEAR));
		endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, model.toPattern()));
		JComponent de_dateSpinner2 = endSpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor2 = (JSpinner.DefaultEditor)de_dateSpinner2;
		spinnerEditor2.getTextField().setHorizontalAlignment(JTextField.CENTER);
		manage.add(endSpinner);
		
		JSeparator separator_2 = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator_2, 10, SpringLayout.SOUTH, endLabel);
		sl_manage.putConstraint(SpringLayout.WEST, separator_2, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, separator_2, -10, SpringLayout.EAST, manage);
		manage.add(separator_2);

		confirmButton = new JButton("CONFIRM");
		sl_manage.putConstraint(SpringLayout.NORTH, confirmButton, 10, SpringLayout.SOUTH, separator_2);
		sl_manage.putConstraint(SpringLayout.WEST, confirmButton, 10, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.EAST, confirmButton, -10, SpringLayout.EAST, manage);
		manage.add(confirmButton);
		
		

		productIDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					long product_id = Long.parseLong(productIDField.getText());
					Object[] product = db.fetchProductByID(product_id);
					if (product != null) {
						productNameField.setText(product[4].toString());
					} else {
						productNameField.setText("");
					}
				} catch (NumberFormatException | NullPointerException e1) {
					productNameField.setText("");
				}
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
					if (db.insertNewPromo(new Object[] {
							Long.parseLong(productIDField.getText()),
							promoTitleField.getText(),
							conditionField.getText(),
							(double) discountSpinner.getValue(),
							startFinal, endFinal})) {
						JOptionPane.showMessageDialog(
							null, "Successfully inserted new promo.", 
							"Success | " + Main.SYSTEM_NAME, 
							JOptionPane.INFORMATION_MESSAGE);
						clearFields();
						displayPromos();
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
				displayPromos();
				
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
		gl.getAllComponentsChangeTheme(this, 8);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
	}
	
	private void displayPromos() {
		ArrayList<ArrayList<ArrayList<Object>>> data = db.fetchPromos();
		
		StringBuilder sb = new StringBuilder("ACTIVE PROMOS:\n");
		data.get(0).forEach(
			(active) ->  sb.append(
				(!active.isEmpty()) 
				? String.format("• [%d]: %s-%s\n  %s %s %s\n", 
					active.get(0), active.get(4), active.get(5), 
					((double) active.get(3) < 1) ? ((double) active.get(3) * 100) + "%"
						: String.format("Php %,.2f OFF ", active.get(3)),
					active.get(1), active.get(2)
					)
				: ""
			)
		);
		sb.append("\nINACTIVE PROMOS:\n");
		data.get(1).forEach(
			(inactive) ->  sb.append(
				(!inactive.isEmpty()) 
				? String.format("• [%d]: %s-%s\n  %s %s %s\n", 
					inactive.get(0), inactive.get(4), inactive.get(5), 
					((double) inactive.get(3) < 1) ? ((double) inactive.get(3) * 100) + "%"
						: String.format("Php %,.2f OFF ", inactive.get(3)),
					inactive.get(1), inactive.get(2)
					)
				: ""
			)
		);
		
		textArea.setText(sb.toString());
	}
	
	private boolean checkFields() {
		StringBuilder errors = new StringBuilder("Please check your inputs:\n");
		int init = errors.length();
		
		if (productNameField.getText().isBlank()) errors.append("• Product ID is invalid.\n");
		if (promoTitleField.getText().isBlank()) errors.append("• Promo title cannot be empty.\n");
		if (conditionField.getText().isBlank()) errors.append("• Condition cannot be empty.\n");
		if (((double) discountSpinner.getValue()) == 0) errors.append("• Discount cannot be zero.\n");
		
		try {
			String start = startSpinner.getValue().toString();
			String end = endSpinner.getValue().toString();
			
			DateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
			
			Date parsedStart = parser.parse(start);
			Date parsedEnd = parser.parse(end);
			
			if (parsedStart.after(parsedEnd)) errors.append("• Promo start date cannot be after it ended, it does not make sense.\n");
			if (parsedStart.equals(parsedEnd)) errors.append("• Start and end of promo cannot be the same.\n");
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			startFinal = sdf.format(parsedStart);
			endFinal = sdf.format(parsedEnd);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if (errors.length() > init) {
			JOptionPane.showMessageDialog(
				null, errors.toString(), 
				"Invalid inputs | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE);
			return false;
		} else return true;
	}

	private void clearFields() {
		productIDField.setText("");
		productNameField.setText("");
		promoTitleField.setText("");
		conditionField.setText("");
		discountSpinner.setValue(0);
		startSpinner.setValue(Calendar.getInstance().getTime());
		endSpinner.setValue(Calendar.getInstance().getTime());
	}
}
