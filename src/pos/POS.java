package pos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Utility;

import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.WindowAdapter;
import javax.swing.DefaultListCellRenderer;

import main.Main;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractListModel;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class POS extends JFrame {

	private JPanel container, contentPane;
	private SpringLayout sl_container, sl_contentPane;
	private JScrollPane scrollPane;
	private JLabel customerLabel;
	private JComboBox<String> customerComboBox;
	private JLabel totalLabel;
	private JLabel totalAmount;
	private JTextArea receiptArea;
	private JLabel searchLabel;
	private JTextField searchField;
	private JLabel qtyLabel;
	private JLabel voidLabel;
	private JTextField qtyField;
	private JTextField voidField;
	private JButton qtyButton;
	private JButton voidButton;
	private JButton finishButton;
	private JPanel customer;
	private JTextField idField;
	private JTextField fnameField;
	private JTextField mnameField;
	private JTextField lnameField;
	private JTextField addressField;
	private JLabel contactLabel;
	private JTextField contactField;
	private JButton confirmButton;
	private JLabel operationLabel;
	private JComboBox<String> comboBox;
	private JLabel idLabel;
	private JLabel fnameLabel;
	private JLabel mnameLabel;
	private JLabel lnameLabel;
	private JLabel addressLabel;
	private JList<String> searchList;
	private JMenuItem themeSwitcher;
	private JTextField tenderedField;
	
	private Object[][] data;
	private String[] formattedData;
	private Object[][] customers;
	private String[] formattedCustomers;
	
	private Gallery gl;
	private Utility ut;
	private Database db;
	private Logger log;
	private Receipt rc;
	private long id;
	
	public POS(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl; this.ut = ut; this.db = db; this.log = log; this.id = id;
		rc = new Receipt(db, ut, id);
		
		setTitle("Point of Sales | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 480));
		setSize(1000, 605);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		
		contentPane = new JPanel();
		contentPane.setName("Frame");
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		container = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		sl_container = new SpringLayout();
		container.setLayout(sl_container);
		
		customerLabel = new JLabel("Customer:");
		customerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_container.putConstraint(SpringLayout.NORTH, customerLabel, 10, SpringLayout.NORTH, container);
		sl_container.putConstraint(SpringLayout.WEST, customerLabel, 10, SpringLayout.WEST, container);
		container.add(customerLabel);
		
		customerComboBox = new JComboBox<String>();
		customerComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		customerComboBox.setRenderer(listRenderer);
		sl_container.putConstraint(SpringLayout.NORTH, customerComboBox, -2, SpringLayout.NORTH, customerLabel);
		sl_container.putConstraint(SpringLayout.WEST, customerComboBox, 6, SpringLayout.EAST, customerLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, customerComboBox, 2, SpringLayout.SOUTH, customerLabel);
		container.add(customerComboBox);

		totalLabel = new JLabel("TOTAL:");
		totalLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		sl_container.putConstraint(SpringLayout.WEST, totalLabel, 10, SpringLayout.WEST, container);
		sl_container.putConstraint(SpringLayout.SOUTH, totalLabel, -10, SpringLayout.SOUTH, container);
		container.add(totalLabel);
		
		scrollPane = new JScrollPane();
		sl_container.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, totalLabel);
		scrollPane.setBorder(null);
		sl_container.putConstraint(SpringLayout.EAST, customerComboBox, 0, SpringLayout.EAST, scrollPane);
		sl_container.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, customerLabel);
		sl_container.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, container);
		int frameWidth = this.getSize().width;
		sl_container.putConstraint(SpringLayout.EAST, scrollPane, (int) (frameWidth / 2.5), SpringLayout.WEST, container);
		container.add(scrollPane);

		totalAmount = new JLabel("Php 0.00");
		totalAmount.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_container.putConstraint(SpringLayout.NORTH, totalAmount, 0, SpringLayout.NORTH, totalLabel);
		sl_container.putConstraint(SpringLayout.WEST, totalAmount, 6, SpringLayout.EAST, totalLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, totalAmount, 0, SpringLayout.SOUTH, totalLabel);
		sl_container.putConstraint(SpringLayout.EAST, totalAmount, 0, SpringLayout.EAST, scrollPane);
		
		receiptArea = new JTextArea();
		receiptArea.setEditable(false);
		receiptArea.setMargin(new Insets(10, 10, 10, 10));
		receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane.setViewportView(receiptArea);
		container.add(totalAmount);
		
		searchLabel = new JLabel("Search Products");
		sl_container.putConstraint(SpringLayout.SOUTH, searchLabel, 0, SpringLayout.SOUTH, customerLabel);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_container.putConstraint(SpringLayout.NORTH, searchLabel, 0, SpringLayout.NORTH, customerLabel);
		sl_container.putConstraint(SpringLayout.WEST, searchLabel, 10, SpringLayout.EAST, customerComboBox);
		container.add(searchLabel);
		
		searchField = new JTextField();
		searchField.setFocusTraversalKeysEnabled(false);
		sl_container.putConstraint(SpringLayout.NORTH, searchField, 10, SpringLayout.SOUTH, searchLabel);
		sl_container.putConstraint(SpringLayout.WEST, searchField, 0, SpringLayout.WEST, searchLabel);
		sl_container.putConstraint(SpringLayout.EAST, searchField, -10, SpringLayout.EAST, container);
		container.add(searchField);
		searchField.setColumns(10);
		
		qtyLabel = new JLabel("Quantity:");
		qtyLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_container.putConstraint(SpringLayout.WEST, qtyLabel, 0, SpringLayout.WEST, searchLabel);
		container.add(qtyLabel);
		
		voidLabel = new JLabel("Remove:");
		sl_container.putConstraint(SpringLayout.SOUTH, qtyLabel, -6, SpringLayout.NORTH, voidLabel);
		sl_container.putConstraint(SpringLayout.WEST, voidLabel, 0, SpringLayout.WEST, searchLabel);
		voidLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(voidLabel);
		
		qtyField = new JTextField();
		qtyField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.NORTH, qtyField, -2, SpringLayout.NORTH, qtyLabel);
		sl_container.putConstraint(SpringLayout.WEST, qtyField, 6, SpringLayout.EAST, qtyLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, qtyField, 2, SpringLayout.SOUTH, qtyLabel);
		sl_container.putConstraint(SpringLayout.EAST, qtyField, 50, SpringLayout.EAST, qtyLabel);
		container.add(qtyField);
		qtyField.setColumns(10);
		
		voidField = new JTextField();
		voidField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.NORTH, voidField, -2, SpringLayout.NORTH, voidLabel);
		sl_container.putConstraint(SpringLayout.WEST, voidField, 0, SpringLayout.WEST, qtyField);
		sl_container.putConstraint(SpringLayout.SOUTH, voidField, 2, SpringLayout.SOUTH, voidLabel);
		sl_container.putConstraint(SpringLayout.EAST, voidField, 0, SpringLayout.EAST, qtyField);
		container.add(voidField);
		voidField.setColumns(10);
		
		qtyButton = new JButton("ADD");
		sl_container.putConstraint(SpringLayout.NORTH, qtyButton, 0, SpringLayout.NORTH, qtyField);
		sl_container.putConstraint(SpringLayout.WEST, qtyButton, 0, SpringLayout.EAST, qtyField);
		sl_container.putConstraint(SpringLayout.SOUTH, qtyButton, 0, SpringLayout.SOUTH, qtyField);
		qtyButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		qtyButton.setMargin(new Insets(2, 2, 2, 2));
		sl_container.putConstraint(SpringLayout.EAST, qtyButton, 50, SpringLayout.EAST, qtyField);
		container.add(qtyButton);
		
		voidButton = new JButton("VOID");
		sl_container.putConstraint(SpringLayout.NORTH, voidButton, 0, SpringLayout.NORTH, voidField);
		sl_container.putConstraint(SpringLayout.SOUTH, voidButton, 0, SpringLayout.SOUTH, voidField);
		voidButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		voidButton.setMargin(new Insets(2, 2, 2, 2));
		sl_container.putConstraint(SpringLayout.WEST, voidButton, 0, SpringLayout.WEST, qtyButton);
		sl_container.putConstraint(SpringLayout.EAST, voidButton, 50, SpringLayout.EAST, voidField);
		container.add(voidButton);
		
		finishButton = new JButton("Finish Transaction");
		finishButton.setFocusTraversalKeysEnabled(false);
		finishButton.setMargin(new Insets(2, 2, 2, 2));
		finishButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_container.putConstraint(SpringLayout.NORTH, finishButton, 0, SpringLayout.NORTH, qtyButton);
		sl_container.putConstraint(SpringLayout.SOUTH, finishButton, 0, SpringLayout.SOUTH, voidButton);
		sl_container.putConstraint(SpringLayout.EAST, finishButton, -10, SpringLayout.EAST, container);
		container.add(finishButton);
		
		customer = new JPanel();
		sl_container.putConstraint(SpringLayout.SOUTH, voidLabel, -10, SpringLayout.NORTH, customer);
		sl_container.putConstraint(SpringLayout.NORTH, customer, -210, SpringLayout.SOUTH, container);
		customer.setBorder(new TitledBorder(null, "Manage Customer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_container.putConstraint(SpringLayout.WEST, customer, 0, SpringLayout.WEST, searchLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, customer, -10, SpringLayout.SOUTH, container);
		sl_container.putConstraint(SpringLayout.EAST, customer, -10, SpringLayout.EAST, container);
		container.add(customer);
		SpringLayout sl_customer = new SpringLayout();
		customer.setLayout(sl_customer);
		
		operationLabel = new JLabel("Select Operation:");
		sl_customer.putConstraint(SpringLayout.NORTH, operationLabel, 5, SpringLayout.NORTH, customer);
		sl_customer.putConstraint(SpringLayout.WEST, operationLabel, 10, SpringLayout.WEST, customer);
		customer.add(operationLabel);
		
		comboBox = new JComboBox<String>();
		sl_customer.putConstraint(SpringLayout.EAST, comboBox, 125, SpringLayout.EAST, operationLabel);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ADD", "UPDATE", "REMOVE"}));
		sl_customer.putConstraint(SpringLayout.NORTH, comboBox, -2, SpringLayout.NORTH, operationLabel);
		sl_customer.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, operationLabel);
		sl_customer.putConstraint(SpringLayout.SOUTH, comboBox, 2, SpringLayout.SOUTH, operationLabel);
		customer.add(comboBox);
		
		JSeparator separator_1 = new JSeparator();
		sl_customer.putConstraint(SpringLayout.NORTH, separator_1, 10, SpringLayout.SOUTH, operationLabel);
		sl_customer.putConstraint(SpringLayout.WEST, separator_1, 10, SpringLayout.WEST, customer);
		sl_customer.putConstraint(SpringLayout.EAST, separator_1, -10, SpringLayout.EAST, customer);
		customer.add(separator_1);
		
		idLabel = new JLabel("Customer ID:");
		sl_customer.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.SOUTH, separator_1);
		sl_customer.putConstraint(SpringLayout.WEST, idLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(idLabel);
		
		idField = new JTextField(Long.toString(ut.generateCustomerID(1)));
		idField.setEnabled(false);
		idField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, idField, -2, SpringLayout.NORTH, idLabel);
		sl_customer.putConstraint(SpringLayout.WEST, idField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, idField, 2, SpringLayout.SOUTH, idLabel);
		sl_customer.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, customer);
		customer.add(idField);
		idField.setColumns(10);
		
		fnameLabel = new JLabel("First Name:");
		sl_customer.putConstraint(SpringLayout.NORTH, fnameLabel, 6, SpringLayout.SOUTH, idLabel);
		sl_customer.putConstraint(SpringLayout.WEST, fnameLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(fnameLabel);
		
		fnameField = new JTextField();
		fnameField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, fnameField, -2, SpringLayout.NORTH, fnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, fnameField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, fnameField, 2, SpringLayout.SOUTH, fnameLabel);
		sl_customer.putConstraint(SpringLayout.EAST, fnameField, -10, SpringLayout.EAST, customer);
		customer.add(fnameField);
		fnameField.setColumns(10);
		
		mnameLabel = new JLabel("Middle Name:");
		sl_customer.putConstraint(SpringLayout.NORTH, mnameLabel, 6, SpringLayout.SOUTH, fnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, mnameLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(mnameLabel);
		
		mnameField = new JTextField();
		mnameField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, mnameField, -2, SpringLayout.NORTH, mnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, mnameField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, mnameField, 2, SpringLayout.SOUTH, mnameLabel);
		sl_customer.putConstraint(SpringLayout.EAST, mnameField, -10, SpringLayout.EAST, customer);
		customer.add(mnameField);
		mnameField.setColumns(10);
		
		lnameLabel = new JLabel("Last Name:");
		sl_customer.putConstraint(SpringLayout.NORTH, lnameLabel, 6, SpringLayout.SOUTH, mnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, lnameLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(lnameLabel);
		
		lnameField = new JTextField();
		lnameField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, lnameField, -2, SpringLayout.NORTH, lnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, lnameField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, lnameField, 2, SpringLayout.SOUTH, lnameLabel);
		sl_customer.putConstraint(SpringLayout.EAST, lnameField, -10, SpringLayout.EAST, customer);
		customer.add(lnameField);
		lnameField.setColumns(10);
		
		addressLabel = new JLabel("Address:");
		sl_customer.putConstraint(SpringLayout.NORTH, addressLabel, 6, SpringLayout.SOUTH, lnameLabel);
		sl_customer.putConstraint(SpringLayout.WEST, addressLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(addressLabel);
		
		addressField = new JTextField();
		addressField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, addressField, -2, SpringLayout.NORTH, addressLabel);
		sl_customer.putConstraint(SpringLayout.WEST, addressField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, addressField, 2, SpringLayout.SOUTH, addressLabel);
		sl_customer.putConstraint(SpringLayout.EAST, addressField, -10, SpringLayout.EAST, customer);
		customer.add(addressField);
		addressField.setColumns(10);
		
		contactLabel = new JLabel("Contact #:");
		sl_customer.putConstraint(SpringLayout.NORTH, contactLabel, 6, SpringLayout.SOUTH, addressLabel);
		sl_customer.putConstraint(SpringLayout.WEST, contactLabel, 0, SpringLayout.WEST, operationLabel);
		customer.add(contactLabel);
		
		contactField = new JTextField();
		contactField.setMargin(new Insets(2, 10, 2, 10));
		sl_customer.putConstraint(SpringLayout.NORTH, contactField, -2, SpringLayout.NORTH, contactLabel);
		sl_customer.putConstraint(SpringLayout.WEST, contactField, 0, SpringLayout.WEST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, contactField, 2, SpringLayout.SOUTH, contactLabel);
		sl_customer.putConstraint(SpringLayout.EAST, contactField, -10, SpringLayout.EAST, customer);
		customer.add(contactField);
		contactField.setColumns(10);
		
		confirmButton = new JButton("CONFIRM");
		confirmButton.setMargin(new Insets(2, 2, 2, 2));
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_customer.putConstraint(SpringLayout.NORTH, confirmButton, 0, SpringLayout.NORTH, comboBox);
		sl_customer.putConstraint(SpringLayout.WEST, confirmButton, 10, SpringLayout.EAST, comboBox);
		sl_customer.putConstraint(SpringLayout.SOUTH, confirmButton, 0, SpringLayout.SOUTH, comboBox);
		sl_customer.putConstraint(SpringLayout.EAST, confirmButton, -10, SpringLayout.EAST, customer);
		customer.add(confirmButton);
		
		JSeparator separator = new JSeparator();
		sl_container.putConstraint(SpringLayout.NORTH, separator, 0, SpringLayout.NORTH, qtyButton);
		sl_container.putConstraint(SpringLayout.WEST, separator, 10, SpringLayout.EAST, qtyButton);
		sl_container.putConstraint(SpringLayout.SOUTH, separator, 0, SpringLayout.SOUTH, voidButton);
		separator.setOrientation(SwingConstants.VERTICAL);
		container.add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		sl_container.putConstraint(SpringLayout.NORTH, scrollPane_1, -1, SpringLayout.SOUTH, searchField);
		sl_container.putConstraint(SpringLayout.WEST, scrollPane_1, 0, SpringLayout.WEST, searchField);
		sl_container.putConstraint(SpringLayout.SOUTH, scrollPane_1, -10, SpringLayout.NORTH, qtyLabel);
		sl_container.putConstraint(SpringLayout.EAST, scrollPane_1, 0, SpringLayout.EAST, searchField);
		container.add(scrollPane_1);
		
		searchList = new JList<String>();
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(searchList);
		
		JLabel tenderedLabel = new JLabel("Amount Tendered:");
		sl_container.putConstraint(SpringLayout.WEST, finishButton, 10, SpringLayout.EAST, tenderedLabel);
		tenderedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_container.putConstraint(SpringLayout.NORTH, tenderedLabel, 0, SpringLayout.NORTH, qtyLabel);
		sl_container.putConstraint(SpringLayout.WEST, tenderedLabel, 10, SpringLayout.EAST, separator);
		container.add(tenderedLabel);
		
		tenderedField = new JTextField();
		tenderedField.setHorizontalAlignment(SwingConstants.CENTER);
		sl_container.putConstraint(SpringLayout.NORTH, tenderedField, 0, SpringLayout.NORTH, voidField);
		sl_container.putConstraint(SpringLayout.WEST, tenderedField, 0, SpringLayout.WEST, tenderedLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, tenderedField, 0, SpringLayout.SOUTH, voidField);
		sl_container.putConstraint(SpringLayout.EAST, tenderedField, 0, SpringLayout.EAST, tenderedLabel);
		container.add(tenderedField);
		tenderedField.setColumns(10);


		customerComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] customer = customers[customerComboBox.getSelectedIndex()];
				long customerID = (long) customer[0];
				String customerName = customer[2] + " " + ((customer[3] == null) ? "" : customer[3] + " ") + customer[4];
				rc.setCustomer(
					customerID, (customerID == 1) ? "WALK-IN" : customerName
				);
				refreshReceipt();
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					searchList.setSelectedIndex(0);
					searchList.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String keyword = searchField.getText();
				data = db.fetchProductByKeyword(keyword);
				
				populateList();
			}
		});
		qtyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = searchList.getSelectedIndex();
					long product_id = (long) data[index][0];
					double quantity = Double.parseDouble(qtyField.getText());
					rc.addPurchase(product_id, quantity);
					refreshReceipt();
					searchField.setText("");
					qtyField.setText("");
					data = db.fetchProductByKeyword("");
					populateList();
					searchField.requestFocus();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(
						null, "Invalid quantity. Please input any positive number.", 
						"Invalid input | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
					qtyField.setText("");
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(
						null, "Please select a product from the list.", 
						"Invalid input | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		voidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int receiptOrderNo = Integer.parseInt(voidField.getText());
					Object result = rc.removePurchase(receiptOrderNo);
					if (result == null) {
						JOptionPane.showMessageDialog(
							null, "Invalid receipt order number, please check the "
								+ "receipt model to verify.", 
							"Invalid input | " + Main.SYSTEM_NAME, 
							JOptionPane.WARNING_MESSAGE);
						voidField.setText("");
					} else {
						refreshReceipt();
						data = db.fetchProductByKeyword("");
						populateList();
						voidField.setText("");
						searchField.requestFocus();
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(
						null, "Invalid quantity. Please input any positive number.", 
						"Invalid input | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
					voidField.setText("");
				}
			}
		});
		tenderedField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					double amount = Double.parseDouble(tenderedField.getText());
					rc.setAmountTendered(amount);
					refreshReceipt();
				} catch (NumberFormatException e1) {
					rc.setAmountTendered(0);
					refreshReceipt();
				}
			}
		});
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rc.verify()) {
					JOptionPane.showMessageDialog(
						null, "You can't make a transaction without a purchase.", 
						"Error | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				} else if (!rc.verifyPayment()) {
					JOptionPane.showMessageDialog(
						null, "The amount tendered by the customer did not reached the total bill.", 
						"Insufficient Payment | " + Main.SYSTEM_NAME, 
						JOptionPane.WARNING_MESSAGE);
				} else {
					refreshReceipt();
					int result = JOptionPane.showConfirmDialog(
						null, "Please confirm the transaction.", 
						"Confirmation | " + Main.SYSTEM_NAME, 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.INFORMATION_MESSAGE
					);
					if (result == 0) {
						if (rc.make(id)) {
							JOptionPane.showMessageDialog(
								null, "Transaction successful.", 
								"Sucess | " + Main.SYSTEM_NAME, 
								JOptionPane.INFORMATION_MESSAGE);
							rc = new Receipt(db, ut, id);
							tenderedField.setText("");
						}
					}
				}
			}
		});
		finishButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					searchField.requestFocus();
				}
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == 0) {
					idField.setText(Long.toString(ut.generateCustomerID(1)));
					idField.setEnabled(false);
					fnameField.setEnabled(true);
					mnameField.setEnabled(true);
					lnameField.setEnabled(true);
					addressField.setEnabled(true);
					contactField.setEnabled(true);
				} 
				
				else if (comboBox.getSelectedIndex() == 1) {
					idField.setText("");
					idField.setEnabled(true);
					fnameField.setEnabled(false);
					mnameField.setEnabled(false);
					lnameField.setEnabled(false);
					addressField.setEnabled(false);
					contactField.setEnabled(false);
				} 
				
				else if (comboBox.getSelectedIndex() == 2) {
					idField.setText("");
					idField.setEnabled(true);
					fnameField.setEnabled(false);
					mnameField.setEnabled(false);
					lnameField.setEnabled(false);
					addressField.setEnabled(false);
					contactField.setEnabled(false);
				}
				
				fnameField.setText("");
				mnameField.setText("");
				lnameField.setText("");
				addressField.setText("");
				contactField.setText("");
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == 0) {
					if (checkFields()) {
						Object[] customer = {
							Long.parseLong(idField.getText()),
							0.0, fnameField.getText().toUpperCase(),
							(mnameField.getText().isBlank()) ? null : mnameField.getText().toUpperCase(),
							lnameField.getText().toUpperCase(),
							addressField.getText().toUpperCase(),
							contactField.getText().toUpperCase()};
						if (db.checkCustomer(customer) ) {
							if (db.insertNewCustomer(customer)) {
								JOptionPane.showMessageDialog(
									null, "Customer (" + fnameField.getText() + ") has created his/her new account!", 
									"Sucess | " + Main.SYSTEM_NAME, 
									JOptionPane.INFORMATION_MESSAGE);
							}
							populateCustomers();
							comboBox.setSelectedIndex(0);
						} else {
							JOptionPane.showMessageDialog(
								null, "Customer (" + fnameField.getText() + ") already exists.", 
								"Error | " + Main.SYSTEM_NAME, 
								JOptionPane.WARNING_MESSAGE);
						}
					}
//					idField.setText(Long.toString(ut.generateCustomerID(1)));
//					idField.setEnabled(false);
//					fnameField.setEnabled(true);
//					mnameField.setEnabled(true);
//					lnameField.setEnabled(true);
//					addressField.setEnabled(true);
//					contactField.setEnabled(true);
				} 
				
				else if (comboBox.getSelectedIndex() == 1) {
//					idField.setText("");
//					idField.setEnabled(true);
//					fnameField.setEnabled(false);
//					mnameField.setEnabled(false);
//					lnameField.setEnabled(false);
//					addressField.setEnabled(false);
//					contactField.setEnabled(false);
				} 
				
				else if (comboBox.getSelectedIndex() == 2) {
//					idField.setText("");
//					idField.setEnabled(true);
//					fnameField.setEnabled(false);
//					mnameField.setEnabled(false);
//					lnameField.setEnabled(false);
//					addressField.setEnabled(false);
//					contactField.setEnabled(false);
				}
			}
		});
		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Object[] customer = db.fetchCustomerByID(Long.parseLong(idField.getText()));
					if (customer != null) {
						fnameField.setText(customer[2].toString());
						String mname = (customer[3] == null) ? "" : customer[3].toString();
						mnameField.setText(mname);
						lnameField.setText(customer[4].toString());
						addressField.setText(customer[5].toString());
						contactField.setText(customer[6].toString());
						fnameField.setEnabled(true);
						mnameField.setEnabled(true);
						lnameField.setEnabled(true);
						addressField.setEnabled(true);
						contactField.setEnabled(true);
					} else {
						fnameField.setText("");
						mnameField.setText("");
						lnameField.setText("");
						addressField.setText("");
						contactField.setText("");
						fnameField.setEnabled(false);
						mnameField.setEnabled(false);
						lnameField.setEnabled(false);
						addressField.setEnabled(false);
						contactField.setEnabled(false);
					}
				} catch (NumberFormatException e1) {
					fnameField.setText("");
					mnameField.setText("");
					lnameField.setText("");
					addressField.setText("");
					contactField.setText("");
					fnameField.setEnabled(false);
					mnameField.setEnabled(false);
					lnameField.setEnabled(false);
					addressField.setEnabled(false);
					contactField.setEnabled(false);
				}
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustContainer();
				adjustFonts();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				populateCustomers();
				rc.setCustomer(1, "WALK-IN");
				refreshReceipt();
				
				adjustTheme(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
					
			}
		});
		themeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});

		adjustTheme(false);
		data = db.fetchProductByKeyword("");
		populateList();
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
	
	private void adjustFonts() {
		int minCont = 604;
		
		ut.adjustFont(totalLabel, container, minCont, 14);
		ut.adjustFont(totalAmount, container, minCont, 14);
		ut.adjustFont(finishButton, container, minCont, 11);
	}
	
	private void adjustContainer() {
		int maxWidth = 950;
		int maxHeight = 600;

		int width = container.getSize().width;
		int height = container.getSize().height;
		
		int widthOverflow = ((width - maxWidth) / 2 < 10) ? 10 : (width - maxWidth) / 2;
		int heightOverflow = ((height - maxHeight) / 2 < 10) ? 10 : (height - maxHeight) / 2;
		
		sl_contentPane.putConstraint(SpringLayout.WEST, container, widthOverflow, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -widthOverflow, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, heightOverflow, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -heightOverflow, SpringLayout.SOUTH, contentPane);
		
		int frameWidth = (int) (this.getSize().width / 2.5);
		int receipt = 400;
		sl_container.putConstraint(SpringLayout.EAST, scrollPane, (frameWidth > receipt) ? receipt : frameWidth, SpringLayout.WEST, container);
	}
	
	private void adjustTheme(boolean change) {
		if (change) gl.isDark = (gl.isDark) ? false : true;
		
		gl.designOptionPanes();
		gl.getAllComponentsChangeTheme(this, 9);
		customer.setBorder(
			new TitledBorder(null, "Manage Customer", TitledBorder.LEADING, 
				TitledBorder.TOP, null, (gl.isDark) ? gl.DFONT : gl.LFONT)
			);
		themeSwitcher.setText(
			(gl.isDark) 
			? "Switch to Light Theme" 
			: "Switch to Dark Theme");
	}
	
	private void refreshReceipt() {
		receiptArea.setText(rc.get(true));
		totalAmount.setText(String.format("Php %,.2f", rc.getTotal()));
		receiptArea.setCaretPosition(0);
	}
	
	private void populateList() {
		formatData();
		searchList.setModel(new AbstractListModel<String>() {
			String[] values = formattedData;
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
	}
	
	private void formatData() {
		if (data == null) {
			formattedData = new String[] {"Search for a keyword in the search field."};
		} else if (data.length == 0) {
			formattedData = new String[] {"Keywords \"" + searchField.getText() + "\" found nothing on the database."};
		} else {
			formattedData = new String[data.length];
			int index = 0;
			for (Object[] row : data) {
				if (row != null) {
					String name = (String) row[4];
					double price = (double) row[6];
					
					Object[] promo = db.fetchPromoByID((long) row[0]);
					if (promo != null) {
						name += " " + promo[1] + " " + promo[0];
						price = ((double) (promo[2]) < 1) 
							? price * (1 - (double) (promo[2])) 
							: price - (double) (promo[2]);
					}
					
					formattedData[index] = String.format("[%d]: %s - %s | Price: %,.2f per %s", 
						(long) row[0], (String) row[1], name, price, (String) row[3]);
				index++;
				}
			}
		}
	}
	
	private void populateCustomers() {
		customers = db.fetchDataQuery("customer", "customer_id", "", "customer_id", "ASC");
		formattedCustomers = new String[customers.length];
		
		int index = 0;
		for (Object[] customer : customers) {
			if ((long) (customer[0]) == 1) formattedCustomers[index] = "WALK-IN";
			else formattedCustomers[index] = 
				String.format("%s %s%s : %,.2f", customer[2], (customer[3] == null) 
					? "" : customer[3] + " ", customer[4], customer[1]);
			index++;
		}

		customerComboBox.setModel(new DefaultComboBoxModel<String>(formattedCustomers));
	}
	
	private boolean checkFields() {
		StringBuilder err = new StringBuilder("Please check your inputs:\n");
		int init = err.length();
		
		if (fnameField.getText().isBlank()) err.append("• First name field cannot be empty\n");
		if (lnameField.getText().isBlank()) err.append("• Last name field cannot be empty\n");
		if (addressField.getText().isBlank()) err.append("• Address field cannot be empty\n");
		if (contactField.getText().isBlank()) err.append("• Contact number field cannot be empty\n");
		
		if (err.length() > init) {
			JOptionPane.showMessageDialog(
				null, err.toString(), 
				"Error | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
