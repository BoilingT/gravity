package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import graphics.objects.GameObject;
import graphics.objects.ObjectCollection;
import graphics.objects.ShapeObject;
import main.DrawHandler;

public class Canvas extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;

	public static Canvas instance = null;
	
	public static Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
		}
		
		return instance;
	}
	
	private int WIDTH;
	private int HEIGHT;
	
	private final Shapes drawer = new Shapes();
	private final DrawHandler drawHandler = new DrawHandler();
	public long updateDelay = 0;
	protected double deltaTime = 0;
	private boolean running = true;
	
	public Canvas() {
		setup();
	}
	
	public void mouseMotionAction(MouseEvent e) {
		drawHandler.mouseMotionAction(e);
	}
	
	public void btnAction(Object e, String content) {
		drawHandler.btnAction(e, content);
	}
	
	//Setup
	private void setup() {
		drawHandler.setup();
		drawHandler.draw();
		
		repaint();
	}
	
	//Logic
	private void update() {
		if (Drawing.getInstance().shouldDraw()) {
			drawHandler.update();
			if (updateDelay != Drawing.getInstance().getDelay()) {
				updateDelay = Drawing.getInstance().getDelay();			
			}
		}
	}
	
	//Drawing
	@Override
	public void paintComponent(Graphics g1) {
		this.HEIGHT = this.getHeight();
		this.WIDTH = this.getWidth();
		Graphics2D g = (Graphics2D) g1;
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setStroke(new BasicStroke(3, 1,1));
		drawer.setGraphics2D(g);
		if (drawer.isNull()) {
			System.out.println("Graphics isn't set");
			return;
		}
		Drawing drawing = Drawing.getInstance();
		try {
			if (drawing != null) {
				
				drawObjects(drawing.getObjects());
				if (drawing.getCollections() != null) {
					for (ObjectCollection collection : drawing.getCollections()) {
						if(collection.getObjects().size() <= 0) continue;
						drawObjects(collection.getObjects());
					}
				}
			}			
		} catch (Exception e) {
			System.out.println("Could not draw object");
			e.printStackTrace();
		}
	}
	
	private void drawObjects(ArrayList<ShapeObject> objects) {
		try {
			
			for (ShapeObject object : objects) {
				if (object.getShape() != null) {
					if(object.isFilled()) {
						drawer.fill(object.getShape(), object.getColor());
					}else if(!object.isFilled()){					
						drawer.draw(object.getShape(), object.getColor());
					}
				}else if(object.isText()){
					drawer.write(object.getText(), object.getPos(), 15, object.getColor());
				}
			}
		} catch (Exception e) {
//			System.out.println("Warning: Could not get ShapeObject");
		}
	}
	
	private void wait(int ms) {
		try {
			Thread.sleep(ms);
			
		} catch (Exception e) {
			running = false;
			String msg = String.format("Thread interrupted: %s", e.getMessage());
            
            JOptionPane.showMessageDialog(this, msg, "Error", 
				JOptionPane.ERROR_MESSAGE);
            return;
		}
		
		return;
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		try {
			new Thread(this).start();
			
		} catch (Exception e) {
			System.out.println("Notify error :: Canvas");
		}
	}
	
	@Override
	public void run() {
		long lastTime, timeDiff, delay;
		
		lastTime = System.currentTimeMillis();
		
		while(running) {
			
			this.update();
			this.repaint();
			
			timeDiff = System.currentTimeMillis() - lastTime;
			delay = updateDelay - timeDiff;
			deltaTime = timeDiff / 1000f;
//			System.out.println("delay: " + delay);
//			System.out.println("diff: " + timeDiff);
//			System.out.println("Time Diff: " + timeDiff + " ms");
//			System.out.println("Delay: " + delay + " ms");
			
			if (delay < 0) {
//				deltaTime = 0;
				lastTime = System.currentTimeMillis();
//				System.out.println("true");
				continue;
			}
			
			try {
				if(delay > 0) {
					Thread.sleep(delay);					
				}
			} catch (InterruptedException e) {
				String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
					JOptionPane.ERROR_MESSAGE);
                running = false;
                return;
			}
			
			lastTime = System.currentTimeMillis();
		}
	}
		
	public double deltaTime() {
		return this.deltaTime;
	}

}
