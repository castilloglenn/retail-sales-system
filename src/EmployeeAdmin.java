import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import utils.Database;
import utils.Gallery;
import utils.Utility;

import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class EmployeeAdmin extends JFrame {

	private JPanel container, contentPane, dashboard, alerts, manage, payroll, schedule, crud, notification,
		late, absent;
	private JLabel payrollTitle;
	private JButton managePayroll, generatePayroll;
	private SpringLayout sl_contentPane;
	private JMenuItem themeSwitcher;
	
	private Gallery gl;
	private Utility ut;
	private Database db;

	public EmployeeAdmin(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Employee Management | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(640, 480);
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		addPopup(this, popupMenu);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		container = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		container.setLayout(new BorderLayout(10, 10));
		
		dashboard = new JPanel();
		container.add(dashboard, BorderLayout.CENTER);
		dashboard.setLayout(new SpringLayout());
		
		alerts = new JPanel();
		container.add(alerts, BorderLayout.EAST);
		alerts.setLayout(new GridLayout(3, 0, 0, 10));
		
		notification = new JPanel();
		alerts.add(notification);
		notification.setLayout(new SpringLayout());
		
		late = new JPanel();
		alerts.add(late);
		late.setLayout(new SpringLayout());
		
		absent = new JPanel();
		alerts.add(absent);
		absent.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		
		manage = new JPanel();
		container.add(manage, BorderLayout.SOUTH);
		manage.setLayout(new GridLayout(0, 3, 10, 0));
		
		payroll = new JPanel();
		manage.add(payroll);
		SpringLayout sl_payroll = new SpringLayout();
		payroll.setLayout(sl_payroll);
		
		payrollTitle = new JLabel("EMPLOYEE PAYROLL");
		payrollTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sl_payroll.putConstraint(SpringLayout.NORTH, payrollTitle, 3, SpringLayout.NORTH, payroll);
		sl_payroll.putConstraint(SpringLayout.WEST, payrollTitle, 5, SpringLayout.WEST, payroll);
		sl_payroll.putConstraint(SpringLayout.EAST, payrollTitle, -5, SpringLayout.EAST, payroll);
		payrollTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		payroll.add(payrollTitle);
		
		managePayroll = new JButton("MANAGE PAYROLL");
		sl_payroll.putConstraint(SpringLayout.EAST, managePayroll, 0, SpringLayout.EAST, payrollTitle);
		managePayroll.setMargin(new Insets(1, 14, 1, 14));
		managePayroll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		managePayroll.setBackground(gl.COMP_BACKGROUND);
		sl_payroll.putConstraint(SpringLayout.NORTH, managePayroll, 6, SpringLayout.SOUTH, payrollTitle);
		sl_payroll.putConstraint(SpringLayout.WEST, managePayroll, 0, SpringLayout.WEST, payrollTitle);
		payroll.add(managePayroll);
		
		generatePayroll = new JButton("GENERATE PAYROLL");
		sl_payroll.putConstraint(SpringLayout.EAST, generatePayroll, 0, SpringLayout.EAST, managePayroll);
		generatePayroll.setMargin(new Insets(1, 14, 1, 14));
		generatePayroll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		generatePayroll.setBackground(gl.COMP_BACKGROUND);
		sl_payroll.putConstraint(SpringLayout.NORTH, generatePayroll, 6, SpringLayout.SOUTH, managePayroll);
		sl_payroll.putConstraint(SpringLayout.WEST, generatePayroll, 0, SpringLayout.WEST, payrollTitle);
		payroll.add(generatePayroll);
		
		schedule = new JPanel();
		manage.add(schedule);
		schedule.setLayout(new SpringLayout());
		
		crud = new JPanel();
		manage.add(crud);
		crud.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		
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
				adjustTheme();
			}
		});

		adjustTheme();
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
		int lower = 194;
		
		ut.adjustFont(payrollTitle, payroll, lower, 11);
		ut.adjustFont(managePayroll, payroll, lower, 10);
		ut.adjustFont(generatePayroll, payroll, lower, 10);
	}
	
	private void adjustContainer() {
		int maxWidth = 1200;
		int maxHeight = 700;

		int width = container.getSize().width;
		int height = container.getSize().height;
		
		int widthOverflow = ((width - maxWidth) / 2 < 10) ? 10 : (width - maxWidth) / 2;
		int heightOverflow = ((height - maxHeight) / 2 < 10) ? 10 : (height - maxHeight) / 2;
		
		sl_contentPane.putConstraint(SpringLayout.WEST, container, widthOverflow, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -widthOverflow, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, heightOverflow, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -heightOverflow, SpringLayout.SOUTH, contentPane);
	}
	
	private void adjustTheme() {
		gl.designOptionPanes();
		gl.adjustTheme(new JComponent[] {dashboard, payroll, schedule, crud, notification, late, absent, payrollTitle});
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			contentPane.setBackground(gl.DFRAME_BACKGROUND);
			container.setBackground(gl.DFRAME_BACKGROUND);
			alerts.setBackground(gl.DFRAME_BACKGROUND);
			manage.setBackground(gl.DFRAME_BACKGROUND);
			gl.isDark = false;
		} else {
			contentPane.setBackground(gl.LFRAME_BACKGROUND);
			container.setBackground(gl.LFRAME_BACKGROUND);
			alerts.setBackground(gl.LFRAME_BACKGROUND);
			manage.setBackground(gl.LFRAME_BACKGROUND);
			gl.isDark = true;
		}
	}
}
