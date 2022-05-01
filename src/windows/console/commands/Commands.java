package windows.console.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import global.math.Vector2;
import graphics.Drawing;
import graphics.objects.ShapeObject;
import windows.console.ConsoleWindow;

public class Commands {
	private Drawing draw; 
	private HashMap<String, Action> commands;
	
	public HashMap<String, Action> getCommands() {
		return commands;
	}
	
	public Commands() {
		draw = Drawing.getInstance();
		
		commands = new HashMap<>();
		addFunc("stop", new Action() {
			
			@Override
			public void run() {
				draw.stop();
			}

			@Override
			public void run(String args) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addFunc("play", new Action("delay") {
			
			@Override
			public void run() {
				draw.play();
			}

			@Override
			public void run(String args) {
				int delay = 0;
				try {
					delay = Integer.parseInt(getArgs(args)[0]);
				}catch (Exception e) {
					return;
				}
				draw.setDelay(delay);
				draw.play();
			}
		});
		
		addFunc("setColor", new Action("index, r, g, b") {
			@Override
			public void run(String args) {
				System.out.println("Args: " + args);
				String[] values;
				Color newColor = null;
				int index = -1;
				try {
					values = getArgs(args);
					if(values.length != 4) return;
					int r, g, b;
					index = Integer.parseInt(values[0]);
					r = Integer.parseInt(values[1]);
					g = Integer.parseInt(values[2]);
					b = Integer.parseInt(values[3]);
					if((float)(r+g+b)/3 > 255) {
						System.out.println("To high!");
					}
					newColor = new Color(r, g, b);
					
				}catch (Exception e) {
					System.out.println("Could not parse!");
					System.out.println(e.getMessage());
					return;
				}
				for(int i = 0; i < values.length; i++) {
					System.out.println("Value " + i + ": " + values[i]);					
				}
				if(index > draw.getObjects().size()) return;
				draw.getObject(index).setColor(newColor);
			}
		});
		
		addFunc("addLine", new Action("x1, y1, x2, y2") {
			
			@Override
			public void run(String args) {
				float x1, x2, y1, y2;
				String[] vals = getArgs(args);
				try {
					x1 = Integer.parseInt(vals[0]);
					y1 = Integer.parseInt(vals[1]);
					x2 = Integer.parseInt(vals[2]);
					y2 = Integer.parseInt(vals[3]);
					
				}catch (Exception e) {
					return;
				}
				draw.addLine(new Vector2<Float>(x1, y1), new Vector2<Float>(x2, y2), "");
			}
		});
		

		addFunc("rotate", new Action("index, theta, offx, offy") {
			
			@Override
			public void run(String args) {
				int index;
				float theta, offx, offy;
				String[] vals = getArgs(args);
				try {
					index = Integer.parseInt(vals[0]);
					theta = Integer.parseInt(vals[1]);
					offx = Integer.parseInt(vals[2]);
					offy = Integer.parseInt(vals[3]);
					
				}catch (Exception e) {
					return;
				}
				draw.rotate(index, theta, offx, offy);
			}			
		});
		
		addFunc("showObjectList", new Action() {
			
			@Override
			public void run(String args) {
				ArrayList<ShapeObject> vals = draw.getObjects();
				for (int i = 0; i < vals.size(); i++) {
					ShapeObject val = vals.get(i);
					ConsoleWindow.getInstance().cout("ID: " + i + " Name: " + val.getName() + " Color: " + val.getColor() + " Postion: " + val.getPos().toString());
				}
			}
		});
	}
	
	private String[] getArgs(String args) {
		return args.substring(args.indexOf('(')+1, args.indexOf(')')).split("[, ]+");
	}
	
	private void addFunc(String cmd, Action actionListener) {
		if (commands == null) {
			System.out.println("commands is null");
			return;
		}
		System.out.println(cmd + " added");
		commands.put(cmd, actionListener);
	}
	
	public void run(String cmd, String args) {
		String[] argsArr = args.split("\\n");
		args = "";
		for (int i = 0; i < argsArr.length; i++) {
			args += argsArr[i];
		}
		if (commands == null) {
			System.out.println("commands = null");
			return;
		}
		if (args.length() > 0) {
			commands.get(cmd).run(args);			
		}else {
			commands.get(cmd).run();			
		}
	}
}
