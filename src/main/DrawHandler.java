package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import graphics.GraphicGeometry;
import graphics.objects.GameObject;
import main.GameObjects.*;
import windows.console.ConsoleWindow;

public class DrawHandler{
	
	private ConsoleWindow console; 
	private GraphicGeometry draw;

	private Vector2<Float> mousePos = new Vector2<Float>(0f, 0f);
	
	private ArrayList<GameObject> GameObjects = new ArrayList<>();
	
	private HyperCube hyperCube = new HyperCube(-0.5f, 0.5f, -0.5f, 0.5f, 1, 1, 1);
	private Cube cube = new Cube(-3.5f, 0.5f, -0.5f, 1, 1, 1);
	private Cube cube2 = new Cube(-0.5f, 3.5f, -2.5f, 1, 1, 1);
	
	public DrawHandler() {
		draw = GraphicGeometry.getInstance();
		console = ConsoleWindow.getInstance();
	}
	
	public void mouseMotionAction(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		mousePos.set(x, y);
	}
	
	public void btnAction(Object e, String textContent) {
		KeyEvent event = (KeyEvent) e;
		int key = event.getKeyCode();
		if (key == KeyEvent.VK_1) {
			hyperCube.distance += 0.1f;			
		}else if(key == KeyEvent.VK_2) {
			hyperCube.distance -= 0.1f;			
		}
		
		else if (key == KeyEvent.VK_3) {
			cube.scale += 10f;			
		}else if(key == KeyEvent.VK_4) {
			cube.scale -= 10f;			
		}
		
		if (key == KeyEvent.VK_W) {
			for (Matrix m : cube.pts) {
				m.getValues()[2][0] += 0.05f; 
			}
		}
		
		if(key == KeyEvent.VK_S) {
			for (Matrix m : cube.pts) {
				m.getValues()[2][0] -= 0.05f; 
			}
		}
		
		if (key == KeyEvent.VK_A) {
			for (Matrix m : cube.pts) {
				m.getValues()[0][0] += 0.05f; 
			}	
		}
		
		if(key == KeyEvent.VK_D) {
			for (Matrix m : cube.pts) {
				m.getValues()[0][0] -= 0.05f; 
			}
		}
		///////////////
		
		///////////////
		if (key == KeyEvent.VK_W) {
			for (Matrix m : hyperCube.pts) {
				m.getValues()[1][0] += 0.05f; 
			}
		}
		
		if(key == KeyEvent.VK_S) {
			for (Matrix m : hyperCube.pts) {
				m.getValues()[1][0] -= 0.05f; 
			}
		}
		
		if (key == KeyEvent.VK_A) {
			for (Matrix m : hyperCube.pts) {
				m.getValues()[0][0] += 0.05f; 
			}
		}
		
		if(key == KeyEvent.VK_D) {
			for (Matrix m : hyperCube.pts) {
				m.getValues()[0][0] -= 0.05f; 
			}
		}
	}	
	
	//Preparation
	public void setup() {
		addGameObjects();
		for (GameObject obj : GameObjects) {
			obj.init();
		}
		console.cout("Setup done!");
	}
	
	public void addGameObjects() {
		GameObjects.add(hyperCube);
		GameObjects.add(cube);	
		GameObjects.add(cube2);	
	}
	
	public void drawGameObjects() {
		for (GameObject obj : GameObjects) {
			draw.addCollection(obj.buildingBlocks());
		}
	}
	
	//Drawing
	public void draw() {
		drawGameObjects();
		console.cout("Drawing done!");
	}
	
	//Execution
	public void update() {
		cubeUpdate();
//		gameObjectUpdate();
		sleep((int)(1/144f*1000));
	}
	
	private void cubeUpdate() {
		hyperCube.rotateUpdate(Math.PI/180*-0.2f);
//		hyperCube.rotateUpdate(0f);
//		cube.rotateUpdate(0f);
		cube.rotateUpdate(Math.PI/180*-0.4f);
		cube2.rotateUpdate(Math.PI/180*0.4f);
	}

	private void gameObjectUpdate() {
		for (GameObject obj : GameObjects) {
			obj.update();
		}
	}
	
	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			return;
		}
	}
}
