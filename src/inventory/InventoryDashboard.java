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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.LogConstants;
import utils.Logger;
import utils.Utility;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Insets;

@SuppressWarnings("serial")
public class InventoryDashboard extends JFrame {
	
	private JMenuItem themeSwitcher;
	private JPanel container;
	private JTextField searchField;
	private SpringLayout springLayout, sl_container;
	private JTable table;
	private JLabel title;
	private JLabel searchLabel;
	private JLabel searchForLabel;
	private JComboBox<String> categories;
	private JLabel orderByLabel;
	private JComboBox<String> sortByColumn;
	private JButton submitButton;
	private JList<String> sortDirection;
	private JScrollPane scrollPane;
	private JPanel buttonsPanel;
	private JButton insertButton;
	private JButton manageButton;
	private JButton statisticsButton;
	private JButton supplierButton;
	private JButton promoButton;
	private JButton exitButton;
	
	private final String[] COLUMNS = {
		"Product ID", "Category", "Quantity", "Unit", 
		"Product Name", "Purchase Value", "Sell Value"
	};
	private final String[] DB_COLUMNS = {
		"product_id", "category", "quantity", "uom",
		"name", "purchase_value", "sell_value"
	};
	private String[] searchArgs = new String[4];

	private Gallery gl;
	private Utility ut;
	
	public InventoryDashboard(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl; this.ut = ut; 
		
		setTitle("Inventory Management | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(600, 400));
		setSize(900, 550);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPopupMenu popupMenu = new JPopupMenu();
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		addPopup(this, popupMenu);
		
		springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		container = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(container);
		sl_container = new SpringLayout();
		container.setLayout(sl_container);
		
		title = new JLabel("PRODUCT INVENTORY");
		sl_container.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.NORTH, container);
		sl_container.putConstraint(SpringLayout.WEST, title, 10, SpringLayout.WEST, container);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		container.add(title);
		
		searchLabel = new JLabel("SEARCH:");
		sl_container.putConstraint(SpringLayout.NORTH, searchLabel, 6, SpringLayout.SOUTH, title);
		sl_container.putConstraint(SpringLayout.WEST, searchLabel, 0, SpringLayout.WEST, title);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(searchLabel);
		
		searchField = new JTextField();
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_container.putConstraint(SpringLayout.NORTH, searchField, -2, SpringLayout.NORTH, searchLabel);
		sl_container.putConstraint(SpringLayout.WEST, searchField, 6, SpringLayout.EAST, searchLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, searchField, 2, SpringLayout.SOUTH, searchLabel);
		searchField.setColumns(10);
		container.add(searchField);
		
		searchForLabel = new JLabel("SEARCH FOR:");
		sl_container.putConstraint(SpringLayout.NORTH, searchForLabel, 6, SpringLayout.SOUTH, searchLabel);
		sl_container.putConstraint(SpringLayout.WEST, searchForLabel, 0, SpringLayout.WEST, title);
		searchForLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(searchForLabel);
		
		categories = new JComboBox<String>();
		categories.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categories.setModel(new DefaultComboBoxModel<String>(COLUMNS));
		sl_container.putConstraint(SpringLayout.NORTH, categories, -2, SpringLayout.NORTH, searchForLabel);
		sl_container.putConstraint(SpringLayout.WEST, categories, 6, SpringLayout.EAST, searchForLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, categories, 2, SpringLayout.SOUTH, searchForLabel);
		sl_container.putConstraint(SpringLayout.EAST, categories, 0, SpringLayout.EAST, searchField);
		categories.setSelectedIndex(4);
		container.add(categories);
		
		orderByLabel = new JLabel("ORDER BY:");
		sl_container.putConstraint(SpringLayout.NORTH, orderByLabel, 6, SpringLayout.SOUTH, searchForLabel);
		sl_container.putConstraint(SpringLayout.WEST, orderByLabel, 0, SpringLayout.WEST, title);
		orderByLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(orderByLabel);
		
		sortByColumn = new JComboBox<String>();
		sortByColumn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sortByColumn.setModel(new DefaultComboBoxModel<String>(COLUMNS));
		sl_container.putConstraint(SpringLayout.NORTH, sortByColumn, -2, SpringLayout.NORTH, orderByLabel);
		sl_container.putConstraint(SpringLayout.WEST, sortByColumn, 6, SpringLayout.EAST, orderByLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, sortByColumn, 2, SpringLayout.SOUTH, orderByLabel);
		sl_container.putConstraint(SpringLayout.EAST, sortByColumn, 0, SpringLayout.EAST, searchField);
		container.add(sortByColumn);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setMargin(new Insets(2, 2, 2, 2));
		sl_container.putConstraint(SpringLayout.EAST, searchField, -10, SpringLayout.WEST, submitButton);
		sl_container.putConstraint(SpringLayout.NORTH, submitButton, -2, SpringLayout.NORTH, searchField);
		sl_container.putConstraint(SpringLayout.WEST, submitButton, -90, SpringLayout.EAST, container);
		sl_container.putConstraint(SpringLayout.SOUTH, submitButton, 2, SpringLayout.SOUTH, searchField);
		sl_container.putConstraint(SpringLayout.EAST, submitButton, -10, SpringLayout.EAST, container);
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(submitButton);
		
		sortDirection = new JList<String>();
		sortDirection.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sortDirection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_container.putConstraint(SpringLayout.NORTH, sortDirection, 6, SpringLayout.SOUTH, submitButton);
		sl_container.putConstraint(SpringLayout.WEST, sortDirection, 0, SpringLayout.WEST, submitButton);
		sl_container.putConstraint(SpringLayout.SOUTH, sortDirection, 0, SpringLayout.SOUTH, sortByColumn);
		sl_container.putConstraint(SpringLayout.EAST, sortDirection, 0, SpringLayout.EAST, submitButton);
		sortDirection.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"ASCENDING", "DESCENDING"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		sortDirection.setSelectedIndex(0);
		container.add(sortDirection);
		
		scrollPane = new JScrollPane();
		sl_container.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, sortByColumn);
		sl_container.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, title);
		sl_container.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, container);
		scrollPane.setViewportBorder(null);
		container.add(scrollPane);
		addPopup(scrollPane, popupMenu);
		
		buttonsPanel = new JPanel();
		sl_container.putConstraint(SpringLayout.WEST, buttonsPanel, -100, SpringLayout.EAST, container);
		sl_container.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.WEST, buttonsPanel);
		sl_container.putConstraint(SpringLayout.NORTH, buttonsPanel, 0, SpringLayout.NORTH, scrollPane);
		
		table = new JTable(1, 7);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(20);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		sl_container.putConstraint(SpringLayout.SOUTH, buttonsPanel, -10, SpringLayout.SOUTH, container);
		sl_container.putConstraint(SpringLayout.EAST, buttonsPanel, -10, SpringLayout.EAST, container);
		container.add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		insertButton = new JButton("NEW");
		insertButton.setMargin(new Insets(2, 2, 2, 2));
		insertButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(insertButton);
		
		manageButton = new JButton("MANAGE");
		manageButton.setMargin(new Insets(2, 2, 2, 2));
		manageButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(manageButton);
		
		statisticsButton = new JButton("STATISTICS");
		statisticsButton.setMargin(new Insets(2, 2, 2, 2));
		statisticsButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(statisticsButton);
		
		supplierButton = new JButton("SUPPLIERS");
		supplierButton.setMargin(new Insets(2, 2, 2, 2));
		supplierButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(supplierButton);
		
		promoButton = new JButton("PROMOS");
		promoButton.setMargin(new Insets(2, 2, 2, 2));
		promoButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(promoButton);
		
		exitButton = new JButton("EXIT");
		exitButton.setMargin(new Insets(2, 2, 2, 2));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonsPanel.add(exitButton);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSearchArgs();
				table.setModel(
					ut.generateTable(
						db.fetchDataQuery(
							"product", searchArgs[1], searchArgs[0], 
							searchArgs[2], searchArgs[3]), COLUMNS
					)
				);
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InventoryNew(gl, ut, db);
			}
		});
		manageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InventoryManage(gl, db);
			}
		});
		statisticsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InventoryStatistics(gl, db);
			}
		});
		supplierButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SupplierAdmin(gl, ut, db, log, id);
			}
		});
		promoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PromoAdmin(gl, ut, db, log, id);
			}
		});
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date();
				String dt = "OUT: " + sdf.format(date);
				
				log.newLog(id, LogConstants.ATTENDANCE, LogConstants.MAIN, dt);
				dispose();
			}
		});
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == 0) {
					adjustContainer();
					adjustFonts();
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
		themeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme(true);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				table.setModel(ut.generateTable(db.fetchDataQuery("product", "product_id", "", "product_id", "ASC"), COLUMNS));
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				for(int col=0; col < 7; col++){
					table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
			    };
				adjustTheme(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
					DateFormat sdf = new SimpleDateFormat("HH:mm");
					Date date = new Date();
					String dt = "OUT: " + sdf.format(date);
					
					log.newLog(id, LogConstants.ATTENDANCE, LogConstants.MAIN, dt);
			}
		});
		
		adjustTheme(false);
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
	
	private void adjustContainer() {
		int maxWidth = 1100;
		int maxHeight = 600;

		int width = container.getSize().width;
		int height = container.getSize().height;
		
		int widthOverflow = (width < maxWidth || (width - maxWidth) / 2 < 10) ? 10 : (width - maxWidth) / 2;
		int heightOverflow = (height < maxHeight || (height - maxHeight) / 2 < 10) ? 10 : (height - maxHeight) / 2;
		
		springLayout.putConstraint(SpringLayout.WEST, container, widthOverflow, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, container, -widthOverflow, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, container, heightOverflow, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, container, -heightOverflow, SpringLayout.SOUTH, getContentPane());
	}
	
	private void adjustFonts() {
		int minCont = 564;
		int minBut = 120;
		
		ut.adjustFont(title, container, minCont, 20);
		ut.adjustFont(insertButton, buttonsPanel, minBut, 10);
		ut.adjustFont(manageButton, buttonsPanel, minBut, 10);
		ut.adjustFont(statisticsButton, buttonsPanel, minBut, 10);
		ut.adjustFont(supplierButton, buttonsPanel, minBut, 10);
		ut.adjustFont(promoButton, buttonsPanel, minBut, 10);
		ut.adjustFont(exitButton, buttonsPanel, minBut, 10);
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
	
	public void resetFields() {
		searchField.setText("");
		categories.setSelectedIndex(4);
		sortByColumn.setSelectedIndex(0);
		sortDirection.setSelectedIndex(0);
	}
	
	public void getSearchArgs() {
		searchArgs[0] = "%" + searchField.getText() + "%";
		searchArgs[1] = DB_COLUMNS[categories.getSelectedIndex()];
		searchArgs[2] = DB_COLUMNS[sortByColumn.getSelectedIndex()];
		searchArgs[3] = (sortDirection.getSelectedIndex() == 0) ? "ASC" : "DESC";
	}
}
