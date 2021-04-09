package inventory;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import main.Main;
import utils.Database;
import utils.Gallery;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Insets;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class InventoryStatistics extends JDialog {
	
	private JMenuItem themeSwitcher;

	private Gallery gl;
	private Database db;
	
	public InventoryStatistics(Gallery gl, Database db) {
		this.gl = gl; this.db = db;
		
		setTitle("Product Statistics | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(400, 500);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		themeSwitcher = new JMenuItem("Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("PRODUCT STATISTICS");
		title.setBounds(10, 20, 226, 25);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 344, 2);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 56, 344, 372);
		panel.add(scrollPane);
		
		JTextArea textField = new JTextArea();
		textField.setText(constructStatistics());
		textField.setMargin(new Insets(10, 10, 10, 10));
		textField.setFont(new Font("Dialog", Font.BOLD, 12));
		textField.setEditable(false);
		textField.setCaretPosition(0);
		scrollPane.setViewportView(textField);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		addPopup(textField, popupMenu);
		
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
	
	public String constructStatistics() {
		String mepn = nullHandler(db.fetchMaxPurchaseValue());
		double mepv = nullHandler(db.fetchPurchaseValueByName(mepn));
		String mcpn = nullHandler(db.fetchMinPurchaseValue());
		double mcpv = nullHandler(db.fetchPurchaseValueByName(mcpn));
		String mespn = nullHandler(db.fetchMaxSellValue());
		double mespv = nullHandler(db.fetchSellValueByName(mespn));
		String mcspn = nullHandler(db.fetchMinSellValue());
		double mcspv = nullHandler(db.fetchSellValueByName(mcspn));
		String pwtms = nullHandler(db.fetchProductMaxStock());
		String pwtls = nullHandler(db.fetchProductMinStock());
		int tnop = db.fetchTotalCount();
		double tnopq = db.fetchSumQuantity();
		int tnoc = (db.fetchProductCategories()[0].equals("--No existing record--")) 
				? 0 : db.fetchProductCategories().length;
		String pcwtmp = nullHandler(db.fetchCategoryMostProduct());
		String pcwtlp = nullHandler(db.fetchCategoryLeastProduct());
		double app = db.fetchAveragePurchasePrice();
		double asp = db.fetchAverageSellingPrice();
		double paoapoh = db.fetchSumProductPurchase();
		double saoapoh = db.fetchSumProductSell();
		
		String report = String.format(
			  "Total Number of Products: %d\n"
			+ "Total Number of Product Quantity: %,.2f\n"
			+ "Total Number of Categories: %d\n\n"
			+ "Most Expensive Purchased Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Cheapest Purchased Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Expensive Selling Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Cheapest Selling Product: \n• %s priced at Php %,.2f\n\n"
			+ "Product with the Most Stocks: \n• %s\n\n"
			+ "Product with the Least Stocks: \n• %s\n\n"
			+ "Product Category with the Most Products: \n• %s\n\n"
			+ "Product Category with the Least Products: \n• %s\n\n"
			+ "Average Purchase Price: \n• Php %,.2f\n\n"
			+ "Average Selling Price: \n• Php %,.2f\n\n"
			+ "Purchase Amount of all Products On-hand: \n• Php %,.2f\n\n"
			+ "Selling Amount of all Products On-hand: \n• Php %,.2f",
			tnop, tnopq, tnoc, mepn, mepv, mcpn, mcpv, mespn, mespv, mcspn, 
			mcspv, pwtms, pwtls, pcwtmp, pcwtlp, app, asp, paoapoh, saoapoh
		);
		
		return report;
	}
	
	public String nullHandler(String message) {
		return (message == null || message.equals("--No existing record--")) ? "none" : message.toUpperCase();
	}
	
	public double nullHandler(double value) {
		return (value == -1) ? 0 : value;
	}
}
