import utils.Database;
import utils.Gallery;
import utils.Utility;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import javax.swing.JPasswordField;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JToggleButton;

public class Main {
	
	public static final String SYSTEM_NAME = "Primordial Retail & Sales Management System";

	private JFrame mainFrame;
	private JPasswordField idField, passField;
	private JLabel title, logo, loginHeader, idLabel, passLabel, forgotLabel;
	private JButton submitButton, passVisibility;
	private JToggleButton idVisibility, theme;
	private JPanel titlePanel, loginPanel;
	
	private Gallery gl;
	private Database db;
	private Utility ut;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		gl = new Gallery();
		ut = new Utility();
		db = new Database(ut);

//		if (db.fetchManagers() == 0) {
//			new SetupSystem(gl, ut, db);
//		} else {
//			initialize();
//		}
		new EmployeeAdmin(gl, ut, db);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Secure Login");
		mainFrame.setSize(400, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setIconImage(gl.loginIcon);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setLayout(null);
		
		title = new JLabel(
			"<html>"
				+ "<p style=\"text-align:center;\">"
					+ Main.SYSTEM_NAME.toUpperCase()
				+ "</p>"
			+ "</html>"
		);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.setBounds(84, 11, 290, 64);
		mainFrame.getContentPane().add(title);
		
		logo = new JLabel();
		title.setLabelFor(logo);
		logo.setBounds(10, 11, 64, 64);
		mainFrame.getContentPane().add(logo);
		
		loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		loginPanel.setBounds(10, 86, 364, 164);
		mainFrame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		loginHeader = new JLabel("LOGIN");
		loginHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginHeader.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeader.setBounds(10, 5, 344, 24);
		loginPanel.add(loginHeader);
		
		idLabel = new JLabel("EMPLOYEE ID:");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLabel.setBounds(15, 34, 102, 24);
		loginPanel.add(idLabel);
		
		passLabel = new JLabel("PASSWORD:");
		passLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		passLabel.setBounds(15, 63, 102, 24);
		loginPanel.add(passLabel);
		
		idField = new JPasswordField();
		idField.setFocusTraversalKeysEnabled(false);
		idField.setToolTipText("Enter your employee ID.");
		idField.setBounds(127, 34, 193, 24);
		loginPanel.add(idField);
		
		passField = new JPasswordField();
		passField.setToolTipText("Please enter your password.");
		passField.setFocusTraversalKeysEnabled(false);
		passField.setBounds(127, 63, 193, 24);
		loginPanel.add(passField);
		
		forgotLabel = new JLabel(
			"<html>"
				+ "<u>"
					+ "Forgot password? Request password retrieval here."
				+ "</u>"
			+ "</html>"
		);
		forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		forgotLabel.setToolTipText(
			"<html>"
				+ "This will request a password retrieval notice to your<br>"
				+ "supervisor/manager in order to reset your password."
			+ "</html>"
		);
		forgotLabel.setHorizontalAlignment(SwingConstants.CENTER);
		forgotLabel.setBounds(64, 98, 239, 14);
		loginPanel.add(forgotLabel);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setBackground(gl.COMP_BACKGROUND);
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		submitButton.setToolTipText("You can also press enter to submit.");
		submitButton.setBounds(45, 123, 309, 30);
		loginPanel.add(submitButton);
		
		idVisibility = new JToggleButton();
		idVisibility.setBackground(gl.COMP_BACKGROUND);
		idVisibility.setIcon(gl.resizedShow);
		idVisibility.setSelectedIcon(gl.resizedHide);
		idVisibility.setBounds(319, 34, 35, 24);
		loginPanel.add(idVisibility);
		
		passVisibility = new JButton(gl.resizedShow);
		passVisibility.setBackground(gl.COMP_BACKGROUND);
		passVisibility.setBounds(319, 63, 35, 24);
		loginPanel.add(passVisibility);
		
		theme = new JToggleButton();
		theme.setBackground(gl.COMP_BACKGROUND);
		theme.setIcon(gl.resizedDark);
		theme.setBounds(10, 123, 35, 30);
		loginPanel.add(theme);
		
		titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		titlePanel.setBounds(84, 11, 290, 64);
		mainFrame.getContentPane().add(titlePanel);

		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9 || e.getKeyCode() == 10) {
					passField.requestFocus();
				}
			}
		});
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
					// Open Portal Here
				}
			}
		});
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					idField.requestFocus();
				} else if (e.getKeyCode() == 10) {
					submitButton.doClick();
					passField.setText(null);
					idField.requestFocus();
				}
			}
		});
		idVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idVisibility.isSelected()) {
					idField.setEchoChar('\u0000');
				} else if (!idVisibility.isSelected()) {
					idField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
				}
			}
		});
		passVisibility.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passVisibility.setIcon(gl.resizedHide);
				passField.setEchoChar('\u0000');
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				passVisibility.setIcon(gl.resizedShow);
				passField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
			}
		});
		forgotLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (theme.isSelected()) {
					forgotLabel.setForeground(gl.LFONT_HOVER);
				} else {
					forgotLabel.setForeground(gl.DFONT_HOVER);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (theme.isSelected()) {
					forgotLabel.setForeground(gl.LFONT);
				} else {
					forgotLabel.setForeground(gl.DFONT);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Open password retrieval window here
			}
		});
		theme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustTheme();
			}
		});
		
		adjustTheme();
		mainFrame.setVisible(true);
	}
	
	private void adjustTheme() {
		gl.designOptionPanes();
		gl.adjustTheme(new JComponent[] {title, loginPanel, loginHeader, 
				idLabel, passLabel, forgotLabel, titlePanel});
		
		if (gl.isDark) {
			theme.setIcon(gl.resizedDark);
			logo.setIcon(gl.resizedDarkLogo);
			mainFrame.getContentPane().setBackground(gl.DFRAME_BACKGROUND);
			gl.isDark = false;
		} else {
			theme.setIcon(gl.resizedLight);
			logo.setIcon(gl.resizedLightLogo);
			mainFrame.getContentPane().setBackground(gl.LFRAME_BACKGROUND);
			gl.isDark = true;
		}
	}
	
	private boolean checkFields() {
		String[] errors = new String[2];
		String id = new String(idField.getPassword());
		String pass = new String(passField.getPassword());
		boolean flagged = false;
		
		if (id.isBlank()) errors[0] = "• ID field cannot be empty.\n";
		if (pass.isBlank()) errors[1] = "• Password field cannot be empty.";
		
		String message = "Please check your inputs:\n";
		for (String err : errors) {
			if (err != null) {
				flagged = true;
				message += err;
			}
		}

		if (flagged) {
			JOptionPane.showMessageDialog(
				null, message, "Invalid input | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE
			);
			return false;
		} else {
			if (db.checkLogin(id, pass)) {
				System.out.println("CORRECT!");
			} else {
				JOptionPane.showMessageDialog(
					null, "Incorrect ID or Password", "Invalid input | " + Main.SYSTEM_NAME, 
					JOptionPane.WARNING_MESSAGE
				);
			}
		}
		
		return false;
	}
	
}
