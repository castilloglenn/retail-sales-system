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
import utils.LogConstants;
import utils.Logger;
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
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class PromoAdmin extends JDialog {
	
	private String[] COLUMNS = {
			"Supplier ID", "Name", "Address", "Contact #"
	};
	private Object[] data;
	
	private JPanel container;
	private JMenuItem themeSwitcher;

	private Gallery gl;
	private Utility ut;
	private Database db;
	private Logger log;
	private long id;
	private JTextField productIDField;
	private JTextField productNameField;
	private JTextField promoTitleField;
	private JTextField conditionField;
	
	public PromoAdmin(Gallery gl, Utility ut, Database db, Logger log, long id) {
		setResizable(false);
		this.gl = gl; this.ut = ut; this.db = db; this.log = log; this.id = id;
		
		setTitle("Manage Promos | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(480, 360));
		setSize(640, 360);
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
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 584, 279);
		container.add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		scrollPane.setViewportView(textArea);
		
		JPanel manage = new JPanel();
		panel.add(manage);
		SpringLayout sl_manage = new SpringLayout();
		manage.setLayout(sl_manage);
		
		JLabel manageTitle = new JLabel("Select Operation:");
		sl_manage.putConstraint(SpringLayout.NORTH, manageTitle, 5, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, manageTitle, 10, SpringLayout.WEST, manage);
		manage.add(manageTitle);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ADD", "UPDATE", "DELETE"}));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		comboBox.setRenderer(listRenderer);
		sl_manage.putConstraint(SpringLayout.NORTH, comboBox, -2, SpringLayout.NORTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, manageTitle);
		sl_manage.putConstraint(SpringLayout.SOUTH, comboBox, 2, SpringLayout.SOUTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, comboBox, -10, SpringLayout.EAST, manage);
		manage.add(comboBox);
		
		JSeparator separator = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator, 10, SpringLayout.SOUTH, manageTitle);
		sl_manage.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, separator, -10, SpringLayout.EAST, manage);
		manage.add(separator);
		
		JLabel productIDLabel = new JLabel("Product ID:");
		sl_manage.putConstraint(SpringLayout.NORTH, productIDLabel, 10, SpringLayout.SOUTH, separator);
		sl_manage.putConstraint(SpringLayout.WEST, productIDLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(productIDLabel);
		
		productIDField = new JTextField();
		productIDField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, productIDField, -2, SpringLayout.NORTH, productIDLabel);
		sl_manage.putConstraint(SpringLayout.SOUTH, productIDField, 2, SpringLayout.SOUTH, productIDLabel);
		sl_manage.putConstraint(SpringLayout.EAST, productIDField, -10, SpringLayout.EAST, manage);
		manage.add(productIDField);
		productIDField.setColumns(10);
		
		JLabel productNameLabel = new JLabel("Product Name:");
		sl_manage.putConstraint(SpringLayout.NORTH, productNameLabel, 10, SpringLayout.SOUTH, productIDLabel);
		sl_manage.putConstraint(SpringLayout.WEST, productNameLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(productNameLabel);
		
		productNameField = new JTextField();
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
		sl_manage.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, separator_1, -10, SpringLayout.EAST, manage);
		manage.add(separator_1);
		
		JLabel promoTitleLabel = new JLabel("Promo Title:");
		sl_manage.putConstraint(SpringLayout.NORTH, promoTitleLabel, 10, SpringLayout.SOUTH, separator_1);
		sl_manage.putConstraint(SpringLayout.WEST, promoTitleLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(promoTitleLabel);
		
		promoTitleField = new JTextField();
		promoTitleField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, promoTitleField, -2, SpringLayout.NORTH, promoTitleLabel);
		sl_manage.putConstraint(SpringLayout.WEST, promoTitleField, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, promoTitleField, 2, SpringLayout.SOUTH, promoTitleLabel);
		sl_manage.putConstraint(SpringLayout.EAST, promoTitleField, -10, SpringLayout.EAST, manage);
		manage.add(promoTitleField);
		promoTitleField.setColumns(10);
		
		JLabel conditionLabel = new JLabel("Condition:");
		sl_manage.putConstraint(SpringLayout.NORTH, conditionLabel, 10, SpringLayout.SOUTH, promoTitleLabel);
		sl_manage.putConstraint(SpringLayout.WEST, conditionLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(conditionLabel);
		
		conditionField = new JTextField();
		conditionField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_manage.putConstraint(SpringLayout.NORTH, conditionField, -2, SpringLayout.NORTH, conditionLabel);
		sl_manage.putConstraint(SpringLayout.WEST, conditionField, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, conditionField, 2, SpringLayout.SOUTH, conditionLabel);
		sl_manage.putConstraint(SpringLayout.EAST, conditionField, -10, SpringLayout.EAST, manage);
		manage.add(conditionField);
		conditionField.setColumns(10);
		
		JLabel discountLabel = new JLabel("Discount:");
		sl_manage.putConstraint(SpringLayout.NORTH, discountLabel, 10, SpringLayout.SOUTH, conditionLabel);
		sl_manage.putConstraint(SpringLayout.WEST, discountLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(discountLabel);
		
		JSpinner discountSpinner = new JSpinner();
		discountSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 99999.0, 1.0));
		sl_manage.putConstraint(SpringLayout.NORTH, discountSpinner, -2, SpringLayout.NORTH, discountLabel);
		sl_manage.putConstraint(SpringLayout.WEST, discountSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, discountSpinner, 2, SpringLayout.SOUTH, discountLabel);
		sl_manage.putConstraint(SpringLayout.EAST, discountSpinner, -10, SpringLayout.EAST, manage);
		manage.add(discountSpinner);
		
		JLabel startLabel = new JLabel("Start:");
		sl_manage.putConstraint(SpringLayout.NORTH, startLabel, 10, SpringLayout.SOUTH, discountLabel);
		sl_manage.putConstraint(SpringLayout.WEST, startLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(startLabel);
		
		JSpinner startSpinner = new JSpinner();
		startSpinner.setModel(new SpinnerDateModel(new Date(1618156800000L), null, null, Calendar.DAY_OF_YEAR));
		sl_manage.putConstraint(SpringLayout.NORTH, startSpinner, -2, SpringLayout.NORTH, startLabel);
		sl_manage.putConstraint(SpringLayout.WEST, startSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, startSpinner, 2, SpringLayout.SOUTH, startLabel);
		sl_manage.putConstraint(SpringLayout.EAST, startSpinner, -10, SpringLayout.EAST, manage);
		manage.add(startSpinner);
		
		JLabel endLabel = new JLabel("End:");
		sl_manage.putConstraint(SpringLayout.NORTH, endLabel, 10, SpringLayout.SOUTH, startLabel);
		sl_manage.putConstraint(SpringLayout.WEST, endLabel, 0, SpringLayout.WEST, manageTitle);
		manage.add(endLabel);
		
		JSpinner endSpinner = new JSpinner();
		sl_manage.putConstraint(SpringLayout.NORTH, endSpinner, -2, SpringLayout.NORTH, endLabel);
		sl_manage.putConstraint(SpringLayout.WEST, endSpinner, 0, SpringLayout.WEST, productNameField);
		sl_manage.putConstraint(SpringLayout.SOUTH, endSpinner, 2, SpringLayout.SOUTH, endLabel);
		sl_manage.putConstraint(SpringLayout.EAST, endSpinner, -10, SpringLayout.EAST, manage);
		endSpinner.setModel(new SpinnerDateModel(new Date(1618156800000L), null, null, Calendar.DAY_OF_YEAR));
		manage.add(endSpinner);
		
		JSeparator separator_2 = new JSeparator();
		sl_manage.putConstraint(SpringLayout.NORTH, separator_2, 10, SpringLayout.SOUTH, endLabel);
		sl_manage.putConstraint(SpringLayout.WEST, separator_2, 0, SpringLayout.WEST, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, separator_2, -10, SpringLayout.EAST, manage);
		manage.add(separator_2);
		
		JButton confirmButton = new JButton("CONFIRM");
		sl_manage.putConstraint(SpringLayout.NORTH, confirmButton, 10, SpringLayout.SOUTH, separator_2);
		sl_manage.putConstraint(SpringLayout.WEST, confirmButton, 0, SpringLayout.WEST, manageTitle);
		sl_manage.putConstraint(SpringLayout.EAST, confirmButton, -10, SpringLayout.EAST, manage);
		manage.add(confirmButton);
		
		
		
		
		
		
		
		
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
		gl.getAllComponentsChangeTheme(this, 8);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
