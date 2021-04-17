package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import pos.POS;
import utils.Database;
import utils.Gallery;
import utils.LogConstants;
import utils.Logger;
import utils.Utility;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import employee.EmployeeAdmin;
import inventory.InventoryDashboard;

import java.awt.Insets;

@SuppressWarnings("serial")
public class Portal extends JFrame {
	
	private JPanel container;
	private JMenuItem themeSwitcher;
	private JLabel title, detail;
	private JButton inventoryButton;
	private JButton employeeButton;
	private JButton logoutButton;
	private JButton posButton;
	private JButton timeinButton;

	private Gallery gl;
	private Logger log;
	private long id;
	
	private Object[] employee;
	private int access;
	
	public Portal(Gallery gl, Utility ut, Database db, Logger log, long id) {
		this.gl = gl;  this.log = log; this.id = id;
		employee = db.fetchEmployeeByID(id);
		access = Integer.parseInt(Long.toString(id).substring(1, 2));
		
		setTitle("Portal | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		
		switch (access) {
		case 0: setSize(440, 265); break;
		case 1: case 2: setSize(440, 340); break;
		case 3: setSize(440, 415); break;
		case 4: case 5: setSize(440, 490); break;
		}

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
		
		title = new JLabel("Welcome, " + employee[1].toString() + "!");
		title.setBounds(10, 10, 384, 29);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
		container.add(title);
		
		detail = new JLabel(String.format("[%s]", employee[0].toString()));
		detail.setBounds(10, 47, 384, 20);
		detail.setHorizontalAlignment(SwingConstants.CENTER);
		detail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		container.add(detail);
		
		posButton = new JButton("POINT OF SALES");
		posButton.setHorizontalAlignment(SwingConstants.LEFT);
		posButton.setFocusable(false);
		posButton.setMargin(new Insets(5, 45, 5, 5));
		posButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		posButton.setIcon(gl.pos);
		placeButton(posButton);
		
		inventoryButton = new JButton("INVENTORY SYSTEM");
		inventoryButton.setHorizontalAlignment(SwingConstants.LEFT);
		inventoryButton.setMargin(new Insets(5, 45, 5, 5));
		inventoryButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		inventoryButton.setFocusable(false);
		inventoryButton.setIcon(gl.inventory);
		placeButton(inventoryButton);
		
		employeeButton = new JButton("MANAGE EMPLOYEES");
		employeeButton.setMargin(new Insets(5, 45, 5, 5));
		employeeButton.setHorizontalAlignment(SwingConstants.LEFT);
		employeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		employeeButton.setFocusable(false);
		employeeButton.setIcon(gl.employee);
		placeButton(employeeButton);
		
		timeinButton = new JButton("TIME IN");
		timeinButton.setFocusable(false);
		timeinButton.setMargin(new Insets(5, 14, 5, 14));
		timeinButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		placeButton(timeinButton);
		
		logoutButton = new JButton("LOG OUT");
		logoutButton.setFocusable(false);
		logoutButton.setMargin(new Insets(5, 14, 5, 14));
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		placeButton(logoutButton);


		posButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new POS(gl, ut, db, log, id);
				dispose();
			}
		});
		inventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InventoryDashboard(gl, ut, db, log, id);
				dispose();
			}
		});
		employeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeAdmin(gl, ut, db, log, id);
				dispose();
			}
		});
		timeinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date();
				String dt = "IN: " + sdf.format(date);
				log.newLog(id, LogConstants.ATTENDANCE, LogConstants.SUB, dt);
				
				JOptionPane.showMessageDialog(
					null, "Successfully time-in at " + sdf.format(date), 
					"TIME IN | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date();
				String dt = "OUT: " + sdf.format(date);
				
				log.newLog(id, LogConstants.ATTENDANCE, LogConstants.MAIN, dt);
				JOptionPane.showMessageDialog(
					null, "Successfully time-out at " + sdf.format(date), 
					"TIME OUT | " + Main.SYSTEM_NAME, JOptionPane.INFORMATION_MESSAGE);
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
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(
					null, "Closing this window will not input a time-out on your duty, "
							+ "to place a time-out, press the \"LOG OUT\" button below.", 
					"Info | " + Main.SYSTEM_NAME, JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) dispose();
			}
		});
		
		adjustTheme(false);
		setResizable(false);
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
		gl.getAllComponentsChangeTheme(this, 5);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
	}
	
	private void placeButton(JButton button) {
		int[] slot = {89, 164, 239, 314, 364};
		String name = button.getText();
		int x_axis = 35;
		int width = 334;
		int height = (name.equals("LOG OUT") || name.equals("TIME IN")) ? 50 : 75;
		
		
		if (access == 0) {
			if (name.equals("TIME IN")) {
				button.setBounds(x_axis, slot[0], width, height);
				container.add(button);
			}
			if (name.equals("LOG OUT")) {
				button.setBounds(x_axis, slot[1] - 25, width, height);
				container.add(button);
			}
		} else {
			if (name.equals("POINT OF SALES")) {
				if (access >= 2) {
					button.setBounds(x_axis, slot[0], width, height);
					container.add(button);
				}
			} else if (name.equals("INVENTORY SYSTEM")) {
				if (access == 1) {
					button.setBounds(x_axis, slot[0], width, height);
					container.add(button);
				} else if (access >= 3) {
					button.setBounds(x_axis, slot[1], width, height);
					container.add(button);
				}
			} else if (name.equals("MANAGE EMPLOYEES")) {
				if (access >= 4) {
					button.setBounds(x_axis, slot[2], width, height);
					container.add(button);
				}
			} else if (name.equals("TIME IN")) {
				switch (access) {
				case 1: case 2:
					button.setBounds(x_axis, slot[1], width, height);
					container.add(button); break;
				case 3:
					button.setBounds(x_axis, slot[2], width, height);
					container.add(button); break;
				case 4: case 5:
					button.setBounds(x_axis, slot[3], width, height);
					container.add(button); break;
				}
			} else {
				switch (access) {
				case 1: case 2:
					button.setBounds(x_axis, slot[2] - 25, width, height);
					container.add(button); break;
				case 3:
					button.setBounds(x_axis, slot[3] - 25, width, height);
					container.add(button); break;
				case 4: case 5:
					button.setBounds(x_axis, slot[4], width, height);
					container.add(button); break;
				}
			}
		}
	}
}
