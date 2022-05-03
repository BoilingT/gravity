package main;

import java.util.ArrayList;

import windows.console.ConsoleWindow;
import windows.main.MainWindow;

public class Main {
	
	private static final int HEIGHT = 700;
	private static final int WIDTH = 1000;
	
	private static MainWindow mainWindow = new MainWindow(WIDTH, HEIGHT);
	private static ConsoleWindow consoleWindow;
	
	public static void main(String[] args) {
		
		consoleWindow = ConsoleWindow.getInstance();
		consoleWindow.init(null);
		mainWindow.init(new Object[] {consoleWindow});
	}
	
//	private static void addValue(int value) {
//		if(values.size() <= 0) values.add(value);
//		if(value >= values.get(values.get(values.size()-1))) {
//			values.add(value);			
//		}else if(value <= values.get(values.get(0))) {
//			values.add(0, value);
//		}
//	}
	

}