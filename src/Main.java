import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JPasswordField;
import java.awt.Cursor;
import javax.swing.JButton;
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
	private JPasswordField passField;
	private JPasswordField idField;
	
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

		if (db.fetchManagers() == 0)  {
			new SetupSystem(gl, db, ut);
		} else {
			initialize();
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Secure Login");
		mainFrame.setSize(400, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setIconImage(gl.loginIcon);
		mainFrame.getContentPane().setBackground(gl.DFRAME_BACKGROUND);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel(
			"<html>"
				+ "<p style=\"text-align:center;\">"
					+ "PRIMORDIAL RETAIL & SALES<br>MANAGEMENT SYSTEM"
				+ "</p>"
			+ "</html>"
		);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(gl.DFONT);
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.setBounds(84, 11, 290, 64);
		mainFrame.getContentPane().add(title);
		
		JLabel logo = new JLabel(gl.resizedDarkLogo);
		title.setLabelFor(logo);
		logo.setBounds(10, 11, 64, 64);
		mainFrame.getContentPane().add(logo);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		loginPanel.setBackground(gl.DPANEL_BACKGROUND);
		loginPanel.setBounds(10, 86, 364, 164);
		mainFrame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel loginHeader = new JLabel("LOGIN");
		loginHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginHeader.setForeground(gl.DFONT);
		loginHeader.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeader.setBounds(10, 5, 344, 24);
		loginPanel.add(loginHeader);
		
		JLabel idLabel = new JLabel("EMPLOYEE ID:");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLabel.setForeground(gl.DFONT);
		idLabel.setBounds(15, 34, 102, 24);
		loginPanel.add(idLabel);
		
		JLabel passLabel = new JLabel("PASSWORD:");
		passLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		passLabel.setForeground(gl.DFONT);
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
		
		JLabel forgotLabel = new JLabel(
			"<html>"
				+ "<u>"
					+ "Forgot password? Request password retrieval here."
				+ "</u>"
			+ "</html>"
		);
		forgotLabel.setForeground(gl.DFONT);
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
		
		JButton submitButton = new JButton("SUBMIT");
		submitButton.setBackground(gl.DCOMP_BACKGROUND);
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		submitButton.setToolTipText("You can also press enter to submit.");
		submitButton.setBounds(45, 123, 309, 30);
		loginPanel.add(submitButton);
		
		JToggleButton idVisibility = new JToggleButton();
		idVisibility.setBackground(gl.DCOMP_BACKGROUND);
		idVisibility.setIcon(gl.resizedShow);
		idVisibility.setSelectedIcon(gl.resizedHide);
		idVisibility.setBounds(319, 34, 35, 24);
		loginPanel.add(idVisibility);
		
		JButton passVisibility = new JButton(gl.resizedShow);
		passVisibility.setBackground(gl.DCOMP_BACKGROUND);
		passVisibility.setBounds(319, 63, 35, 24);
		loginPanel.add(passVisibility);
		
		JToggleButton theme = new JToggleButton();
		theme.setBackground(gl.DCOMP_BACKGROUND);
		theme.setIcon(gl.resizedDark);
		theme.setBounds(10, 123, 35, 30);
		loginPanel.add(theme);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		titlePanel.setBackground(gl.DPANEL_BACKGROUND);
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
				// Database search and confirmation etc.
				// Show result
				
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
				if (theme.isSelected()) {
					theme.setIcon(gl.resizedLight);
					logo.setIcon(gl.resizedLightLogo);
					title.setForeground(gl.LFONT);
					loginPanel.setBackground(gl.LPANEL_BACKGROUND);
					loginHeader.setForeground(gl.LFONT);
					idLabel.setForeground(gl.LFONT);
					passLabel.setForeground(gl.LFONT);
					forgotLabel.setForeground(gl.LFONT);
					submitButton.setBackground(gl.LCOMP_BACKGROUND);
					idVisibility.setBackground(gl.LCOMP_BACKGROUND);
					passVisibility.setBackground(gl.LCOMP_BACKGROUND);
					titlePanel.setBackground(gl.LPANEL_BACKGROUND);
					mainFrame.getContentPane().setBackground(gl.LFRAME_BACKGROUND);
					gl.isDark = false;
				} else if (!theme.isSelected()) {
					theme.setIcon(gl.resizedDark);
					logo.setIcon(gl.resizedDarkLogo);
					title.setForeground(gl.DFONT);
					loginPanel.setBackground(gl.DPANEL_BACKGROUND);
					loginHeader.setForeground(gl.DFONT);
					idLabel.setForeground(gl.DFONT);
					passLabel.setForeground(gl.DFONT);
					forgotLabel.setForeground(gl.DFONT);
					submitButton.setBackground(gl.DCOMP_BACKGROUND);
					idVisibility.setBackground(gl.DCOMP_BACKGROUND);
					passVisibility.setBackground(gl.DCOMP_BACKGROUND);
					titlePanel.setBackground(gl.DPANEL_BACKGROUND);
					mainFrame.getContentPane().setBackground(gl.DFRAME_BACKGROUND);
					gl.isDark = true;
				}
			}
		});
		
		mainFrame.setVisible(true);
	}
}
