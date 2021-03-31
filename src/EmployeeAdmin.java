import utils.Database;
import utils.Gallery;
import utils.Utility;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class EmployeeAdmin extends JFrame {

	private SpringLayout sl_contentPane;
	private JPanel contentPane, container;
	private JMenuItem themeSwitcher;

	private Gallery gl;
	private Utility ut;
	private Database db;
	
	public EmployeeAdmin(Gallery gl, Utility ut, Database db) {
		this.gl = gl; this.ut = ut; this.db = db;
		
		setTitle("Employee Admin | " + Main.SYSTEM_NAME);
		setIconImage(gl.businessLogo);
		setSize(640, 480);
		setMinimumSize(new Dimension(640, 480));
		setBounds(100, 100, 450, 300);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		themeSwitcher = new JMenuItem((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		popupMenu.add(themeSwitcher);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		container = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, container, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, container, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, container, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, container, -10, SpringLayout.EAST, contentPane);
		contentPane.add(container);
		
		
		
		
		
		

		themeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				adjustTheme();
			}
		});
		
//		adjustTheme();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
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
		int standard = 297;
		int insideStandard = 273;
		
		// ut.adjustFont(formTitle, form, standard, 12);
	}
	
	private void adjustContainer() {
		int maxWidth = 1000;
		int maxHeight = 600;

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
		gl.adjustTheme(new JComponent[] {});
		themeSwitcher.setText((gl.isDark) ? "Switch to Light Theme" : "Switch to Dark Theme");
		
		if (gl.isDark) {
			contentPane.setBackground(gl.DFRAME_BACKGROUND);
			container.setBackground(gl.DFRAME_BACKGROUND);
			gl.isDark = false;
		} else {
			contentPane.setBackground(gl.LFRAME_BACKGROUND);
			container.setBackground(gl.LFRAME_BACKGROUND);
			gl.isDark = true;
		}
	}
}
