package windows.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import global.WindowHandler;
import graphics.Canvas;
import windows.console.ConsoleWindow;

public class MainWindow {
	
	private int WIDTH;
	private int HEIGHT;
	
	private WindowHandler window = new WindowHandler(WIDTH, HEIGHT, "Animation");
	private Canvas canvas = null;
	private ConsoleWindow consoleWindow;

	public MainWindow(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		canvas = Canvas.getInstance();
	}
	
		
	private void initWindowSize() {
		if (window == null) return;
		JPanel sizePanel = new JPanel();
		sizePanel.setOpaque(false);
		sizePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		sizePanel.setSize(new Dimension(WIDTH, HEIGHT));
		window.getContentPane().add(sizePanel);
		window.pack();
		sizePanel = null;
	}
	
	private MouseMotionListener mListener = new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			canvas.mouseMotionAction(e);
			
		}
		
		
	};
		
	public void init(Object[] objects) {
		consoleWindow = (ConsoleWindow) objects[0];
		initWindowSize();
		window.getContentPane().setLayout(null);
		
		canvas.setSize(WIDTH, HEIGHT);
		
		canvas.setLayout(null);
		window.getContentPane().add(canvas);
		window.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				canvas.btnAction(e, null);
			}
		});
		canvas.addMouseMotionListener(mListener);
		window.Show();		
	}
}
