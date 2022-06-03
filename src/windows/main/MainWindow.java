package windows.main;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import global.WindowHandler;
import graphics.Canvas;
import windows.console.ConsoleWindow;

public class MainWindow {
	
	private int WIDTH;
	private int HEIGHT;
	
	private WindowHandler window;
	private Canvas canvas = null;
	private ConsoleWindow consoleWindow;

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
		}
		
		
	};
		
	public void init(Object[] objects) {
		window = new WindowHandler(WIDTH, HEIGHT, "Animation");
		consoleWindow = (ConsoleWindow) objects[0];

		window.getContentPane().setLayout(new GridLayout(1,1));
		
		canvas.setSize(WIDTH, HEIGHT);
		
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
