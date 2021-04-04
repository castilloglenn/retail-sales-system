package employee;

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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import main.Main;
import utils.Database;
import utils.Gallery;
import utils.Utility;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class EmployeeDelete extends JDialog {
	
	private JPanel container;
	private JMenuItem themeSwitcher;

	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public EmployeeDelete(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Delete Employee | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setMinimumSize(new Dimension(320, 240));
		setSize(320, 240);
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
		SpringLayout sl_container = new SpringLayout();
		container.setLayout(sl_container);
		
		JLabel deleteTitle = new JLabel("WARNING: This process is irreversible.");
		sl_container.putConstraint(SpringLayout.EAST, deleteTitle, -10, SpringLayout.EAST, container);
		deleteTitle.setName("");
		sl_container.putConstraint(SpringLayout.NORTH, deleteTitle, 10, SpringLayout.NORTH, container);
		sl_container.putConstraint(SpringLayout.WEST, deleteTitle, 10, SpringLayout.WEST, container);
		container.add(deleteTitle);
		
		
		
		
		
		
		
		
		
		
		
		
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
		gl.getAllComponentsChangeTheme(this, 5);
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			
		} else {
			
		}
	}
}
