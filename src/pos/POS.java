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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.WindowAdapter;
import javax.swing.DefaultListCellRenderer;

import main.Main;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
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
	private JList<String> searchList;
	
	private Gallery gl;
	private Utility ut;
	private Database db;
	private Logger log;
	private long id;
	private JLabel searchLabel;
	private JTextField searchField;
	private JLabel qtyLabel;
	private JLabel voidLabel;
	
	public POS(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl; this.ut = ut; this.db = db; this.log = log; this.id = id;
		
		setTitle("Point of Sales | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(640, 480));
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		JMenuItem themeSwitcher = new JMenuItem("Switch to Dark Theme");
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
		scrollPane.setBorder(null);
		sl_container.putConstraint(SpringLayout.EAST, customerComboBox, 0, SpringLayout.EAST, scrollPane);
		sl_container.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, customerLabel);
		sl_container.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, container);
		sl_container.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, totalLabel);
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
		sl_container.putConstraint(SpringLayout.NORTH, searchField, 10, SpringLayout.SOUTH, searchLabel);
		sl_container.putConstraint(SpringLayout.WEST, searchField, 0, SpringLayout.WEST, searchLabel);
		sl_container.putConstraint(SpringLayout.EAST, searchField, -10, SpringLayout.EAST, container);
		container.add(searchField);
		searchField.setColumns(10);
		
		searchList = new JList<String>();
		searchList.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"sample1", "sample2", "sample3", "sample4", "sample5"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_container.putConstraint(SpringLayout.NORTH, searchList, 0, SpringLayout.SOUTH, searchField);
		sl_container.putConstraint(SpringLayout.WEST, searchList, 0, SpringLayout.WEST, searchLabel);
		sl_container.putConstraint(SpringLayout.SOUTH, searchList, 100, SpringLayout.SOUTH, searchField);
		sl_container.putConstraint(SpringLayout.EAST, searchList, 0, SpringLayout.EAST, searchField);
		container.add(searchList);
		
		qtyLabel = new JLabel("Quantity:");
		qtyLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_container.putConstraint(SpringLayout.NORTH, qtyLabel, 6, SpringLayout.SOUTH, searchList);
		sl_container.putConstraint(SpringLayout.WEST, qtyLabel, 0, SpringLayout.WEST, searchLabel);
		container.add(qtyLabel);
		
		voidLabel = new JLabel("Remove:");
		sl_container.putConstraint(SpringLayout.NORTH, voidLabel, 6, SpringLayout.SOUTH, qtyLabel);
		sl_container.putConstraint(SpringLayout.WEST, voidLabel, 0, SpringLayout.WEST, searchLabel);
		voidLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		container.add(voidLabel);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		
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
	}
	
	private void adjustContainer() {
		int maxWidth = 1100;
		int maxHeight = 600;

		int width = container.getSize().width;
		int height = container.getSize().height;
		
		int widthOverflow = ((width - maxWidth) / 2 < 10) ? 10 : (width - maxWidth) / 2;
		int heightOverflow = ((height - maxHeight) / 2 < 10) ? 10 : (height - maxHeight) / 2;
		
		sl_contentPane.putConstraint(SpringLayout.WEST, container, widthOverflow, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -widthOverflow, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, heightOverflow, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -heightOverflow, SpringLayout.SOUTH, contentPane);
		
		int frameWidth = this.getSize().width;
		sl_container.putConstraint(SpringLayout.EAST, scrollPane, (int) (frameWidth / 2.5), SpringLayout.WEST, container);
	}
	
	private void adjustTheme(boolean change) {
		if (change) gl.isDark = (gl.isDark) ? false : true;
		
		gl.designOptionPanes();
		gl.getAllComponentsChangeTheme(this, 9);
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
