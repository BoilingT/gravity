package global;

import java.awt.Dimension;

import javax.swing.*;

public class WindowHandler extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public WindowHandler(int width, int height, String title) {
		this.setTitle(title);

		//setPreferredSize(new Dimension(width, height));
		setSize(new Dimension(width, height));		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void Show() {
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
