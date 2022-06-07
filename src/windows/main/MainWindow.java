package windows.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import global.WindowHandler;
import graphics.Canvas;
import graphics.Canvas.ObjectType;
import windows.console.ConsoleWindow;

public class MainWindow {
	
	private int WIDTH;
	private int HEIGHT;
	
	private WindowHandler window;
	private Canvas canvas = null;
	private ConsoleWindow consoleWindow;
	private ObjectLayout objectLayout = new ObjectLayout();

	public MainWindow(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		canvas = Canvas.getInstance();
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
			canvas.requestFocus();
		}
		
		
	};
	
	public void init(Object[] objects) {
		window = new WindowHandler(WIDTH, HEIGHT, "Animation");
		consoleWindow = (ConsoleWindow) objects[0];

		window.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints windowC = new GridBagConstraints();
		
		windowC.gridwidth = 1;
		windowC.fill = GridBagConstraints.BOTH;
		windowC.weighty = 1;
		windowC.weightx = 0.1f;
		windowC.gridx = 0;
		windowC.gridy = 0;
		objectLayout.setFocusable(false);
		ObjectLayout.objectSelectBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = ObjectLayout.objectList.getSelectedIndex();
				canvas.drawObject(ObjectType.values()[index], objectLayout.getPos(), objectLayout.getDim(), objectLayout.getRot());
			}
		});
		
		ObjectLayout.updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = ObjectLayout.objectList.getSelectedIndex();
				canvas.drawObject(ObjectType.values()[index], objectLayout.getPos(), objectLayout.getDim(), objectLayout.getRot());				
				canvas.requestFocus();
			}
		});
		window.getContentPane().add(objectLayout, windowC);

		canvas.setSize(WIDTH, HEIGHT);
		canvas.setFocusable(true);
		windowC.weightx = 1f;
		windowC.gridx = 1;
		windowC.gridy = 0;
		window.getContentPane().add(canvas, windowC);
		canvas.addKeyListener(new KeyListener() {
			
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
	
	public void update() {
		int index = ObjectLayout.objectList.getSelectedIndex();
		if(index < 0) {
			return;	
		}
		canvas.drawObject(ObjectType.values()[index], objectLayout.getPos(), objectLayout.getDim(), objectLayout.getRot());
	}	
}
