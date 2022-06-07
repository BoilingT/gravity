package main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import global.math.Vector3;
import graphics.Canvas.ObjectType;
import graphics.GraphicGeometry;
import graphics.objects.GameObject;
import main.GameObjects.*;
import windows.console.ConsoleWindow;

public class DrawHandler{
	
	private ConsoleWindow console; 
	private GraphicGeometry draw;

	private Vector2<Float> mousePos = new Vector2<Float>(0f, 0f);
	
	private ArrayList<GameObject> GameObjects = new ArrayList<>();
	
	private GameObject currentObject = null;
	private HyperCube hyperCube = new HyperCube(-0.5f, 0.5f, -0.5f, 0.5f, 1, 1, 1);
	private Cube2 cube = new Cube2(-3.5f, 0.5f, -0.5f, 1, 1, 1);
	private Cube2 cube2 = new Cube2(-0.5f, 0.5f, -0.5f, 1, 1, 1);
	
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
			cube2.scale += 10f;			
		}else if(key == KeyEvent.VK_4) {
			cube2.scale -= 10f;			
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
//		GameObjects.add(hyperCube);
//		GameObjects.add(cube);	
//		GameObjects.add(cube2);	
	}
	
	public void drawGameObjects() {
		for (GameObject obj : GameObjects) {
			if(!draw.getCollections().contains(obj.buildingBlocks())) {
				draw.addCollection(obj.buildingBlocks());				
			}
			obj.init();
		}
	}
	
	//Drawing
	public void draw() {
		drawGameObjects();
		console.cout("Drawing done!");
	}
	
	private void currentGameObjectDraw() {
		if(currentObject == null) return;
		
		if(!draw.getCollections().contains(currentObject.buildingBlocks())) {
			draw.addCollection(currentObject.buildingBlocks());				
		}
	}
	
	//Execution
	public void update() {
		currentGameObjectDraw();
		cubeUpdate();
		sleep((int)(1/144f*1000));
	}
	
	private void cubeUpdate() {
//		hyperCube.rotateUpdate(Math.PI/180*-0.2f);
//		hyperCube.rotateUpdate(0f);
		if (currentObject != null && currentObject.equals(hyperCube)) {
			hyperCube.rotateUpdate(0f);
		}else if (currentObject != null && currentObject.equals(cube)) {
			cube.rotateUpdate(0f);
		}else if (currentObject != null && currentObject.equals(cube2)) {
			cube2.rotateUpdate(Math.PI/180*0.4f);
		}
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
	
	public void drawObject(ObjectType objectType, Vector3<Float> pos, Vector3<Float> dim, Vector3<Float> rot) {
		draw.getCollections().clear();
		pos = Vector3.scale(pos, 0.01f);
		dim = Vector3.scale(dim, 0.01f);
		rot = Vector3.scale(rot, (float) Math.PI/180f);
		if (objectType.equals(ObjectType.Cube)) {
			currentObject = new Cube(pos.getX(), pos.getY(), pos.getZ(), dim.getX(), dim.getY(), dim.getZ());
		}else if(objectType.equals(ObjectType.HyperCube)) {
			currentObject = hyperCube;
		}else if(objectType.equals(ObjectType.Cube2)) {
			currentObject = cube2;
		}else if(objectType.equals(ObjectType.CubeNPlayer)) {
			currentObject = cube;
		}
		
		currentObject.getTransform().rotation().set(rot);
		currentObject.init();
	}

	public void updateObjectData(ObjectType objectType, Vector3<Float> pos, Vector3<Float> dim, Vector3<Float> rot) {
		if (objectType.equals(ObjectType.Cube)) {
			draw.getCollections().clear();
			pos = Vector3.scale(pos, 0.01f);
			dim = Vector3.scale(dim, 0.01f);
			rot = Vector3.scale(rot, (float) Math.PI/180f);
			currentObject = new Cube(pos.getX(), pos.getY(), pos.getZ(), dim.getX(), dim.getY(), dim.getZ());
			currentObject.getTransform().rotation().set(rot);
			currentObject.init();
		}
	}
}
