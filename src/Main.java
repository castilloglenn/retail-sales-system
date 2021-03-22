import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
	
	private ImageIcon icon = new ImageIcon("images/icon.png");
	private ImageIcon resizedLogo;

	private JFrame frame;
	private JTextField idField;
	private JPasswordField passField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		resizeLogo();
		initialize();
	}
	
	private void resizeLogo() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("images/logo.png"));
		    Image dimg = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		    resizedLogo = new ImageIcon(dimg);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Secure Login");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel(
			"<html>"
				+ "<p style=\"text-align:center;\">"
					+ "PRIMORDIAL RETAIL & SALES<br>MANAGEMENT SYSTEM"
				+ "</p>"
			+ "</html>"
		);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.setBounds(84, 11, 290, 64);
		frame.getContentPane().add(title);
		
		JLabel logo = new JLabel(resizedLogo);
		title.setLabelFor(logo);
		logo.setBounds(10, 11, 64, 64);
		frame.getContentPane().add(logo);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		loginPanel.setBackground(new Color(45, 65, 65));
		loginPanel.setBounds(10, 86, 364, 164);
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel loginHeader = new JLabel("LOGIN");
		loginHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginHeader.setForeground(Color.WHITE);
		loginHeader.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeader.setBounds(10, 5, 344, 24);
		loginPanel.add(loginHeader);
		
		JLabel idLabel = new JLabel("EMPLOYEE ID:");
		idLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLabel.setForeground(Color.WHITE);
		idLabel.setBounds(15, 34, 102, 24);
		loginPanel.add(idLabel);
		
		idField = new JTextField();
		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					passField.requestFocus();
				}
			}
		});
		idField.setToolTipText("Enter your Employee ID.");
		idField.setBounds(127, 34, 224, 24);
		loginPanel.add(idField);
		idField.setColumns(10);
		
		JLabel passLabel = new JLabel("PASSWORD:");
		passLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		passLabel.setForeground(Color.WHITE);
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		passLabel.setBounds(15, 63, 102, 24);
		loginPanel.add(passLabel);
		
		passField = new JPasswordField();
		passField.setToolTipText("Please enter your password.");
		passField.setBounds(127, 63, 224, 24);
		loginPanel.add(passField);
		
		JLabel forgotLabel = new JLabel(
			"<html>"
				+ "<u>"
					+ "Forgot password? Request password retrieval here."
				+ "</u>"
			+ "</html>"
		);
		forgotLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				forgotLabel.setForeground(new Color(35, 240, 199));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				forgotLabel.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Open password retrieval window here
			}
		});
		forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		forgotLabel.setToolTipText(
			"<html>"
				+ "This will request a password retrieval to your<br>"
				+ "supervisor/manager in order to reset your password."
			+ "</html>"
		);
		forgotLabel.setForeground(new Color(255, 255, 255));
		forgotLabel.setHorizontalAlignment(SwingConstants.CENTER);
		forgotLabel.setBounds(64, 98, 239, 14);
		loginPanel.add(forgotLabel);
		
		JButton submitButton = new JButton("SUBMIT");
		submitButton.setBackground(Color.WHITE);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Database search and confirmation etc.
				// Show result
			}
		});
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					submitButton.doClick();
					idField.requestFocus();
				}
			}
		});
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		submitButton.setToolTipText("You can also press enter to submit.");
		submitButton.setBounds(10, 123, 344, 30);
		loginPanel.add(submitButton);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		titlePanel.setBackground(new Color(45, 65, 65));
		titlePanel.setBounds(84, 11, 290, 64);
		frame.getContentPane().add(titlePanel);
	}
}
