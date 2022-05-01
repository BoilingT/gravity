package graphics.experimental;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Canvas2 extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public long updateDelay = 1;
	private Thread updateThread;
	private boolean running = true;
	
	public int s = 10;
	private int c = 0;	
	private int x = 0;
	
	private Color c1 = Color.red;
	private Color c2 = Color.blue;
	
	private int v = 1;
	
	@Override
	public void paintComponent(Graphics g1) {
		updateDelay = s;
		Graphics2D g = (Graphics2D) g1;
		if (c >= this.getWidth()) {
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			c = 0;
			if (x > this.getWidth()*1) {
				x = 0;
			}
			
		}
		
//		if (x >= this.getWidth()/2) {
//			v = -1;
//			Color temp = c1;
//			c1 = c2;
//			c2 = temp;
//			temp = null;
//		}else if(x <= 0) {
//			v = 1;
//			Color temp = c1;
//			c1 = c2;
//			c2 = temp;
//			temp = null;
//		}
		int size = (int) Math.abs((Math.sin(x/10 * Math.PI/180) * 100) + 30);
		int y = 1*x*x/(10000) + (int) (-(Math.sin(x * Math.PI/180)*Math.log10(x*10) * 20));
		
		g.setColor(c2);
		g.fillOval(c, y + this.getHeight()/2, 10, size);
		g.setColor(c2);
		g.fillOval(c, -y + this.getHeight()/2, 10, size);

		g.setColor(c1);
		g.fillOval(c, y + this.getHeight()/2 - 10, 10, size);
		g.setColor(c1);
		
		g.fillOval(c, -y + this.getHeight()/2 - 10, 10, size);
		
//		g.setColor(Color.black);
//		g.drawLine(c+10, 0, c+10, this.getHeight());

		//g.clearRect(c - 300, 0, 10, this.getHeight());
		
		System.out.println(size);
		String val = String.valueOf(s);
		int width = g.getFontMetrics().stringWidth(val);
		g.clearRect(10, 0, width, 10);
		g.drawString(val, 10, 10);
	}
	
	private void update() {
		x+=v;
		c+=v;
		//wait(10);
		
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
		
		updateThread = new Thread(this);
		updateThread.start();
	}
	
	@Override
	public void run() {
		long lastTime, timeDiff, delay;
		
		lastTime = System.currentTimeMillis();
		
		while(running) {
			
			this.repaint();
			this.update();
			
			timeDiff = System.currentTimeMillis() - lastTime;
			delay = updateDelay - timeDiff;
			System.out.println("Time Diff: " + timeDiff + " ms");
			System.out.println("Delay: " + delay + " ms");
			
			if (delay < 0) {
				delay = 1000;
				lastTime = System.currentTimeMillis();
				continue;
			}
			
			try {
				Thread.sleep(delay);
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

}
