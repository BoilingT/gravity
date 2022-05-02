package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import graphics.Canvas;
import graphics.GraphicGeometry;
import graphics.Shapes;
import graphics.objects.GameObject;
import graphics.objects.ObjectCollection;
import graphics.objects.ShapeObject;
import main.scripts.*;
import windows.console.ConsoleWindow;

public class DrawHandler{
	
	private ConsoleWindow console; 
	private GraphicGeometry draw;

	private ShapeObject velText;
	private ShapeObject accText;
	private ShapeObject Text;
	private ShapeObject line;
	private ShapeObject circle;
	private Vector2<Float> mousePos = new Vector2<Float>(0f, 0f);
	
	private ArrayList<GameObject> GameObjects = new ArrayList<>();
	
	private Player player = new Player();
	private Border border = new Border(1000, 700, 1, Color.black);
	private Ball ball = new Ball();
	private Cube cube = new Cube(-0.5f, 0.5f, -0.5f, 1, 1, 1);
	
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
		System.out.println("hej");

//		for (GameObject obj : GameObjects) {
//			obj.update();
//		}
	}	
	
	//Preparation
	public void setup() {

		velText = draw.addText(player.getVel().toString(), new Vector2<Float>(100f, 100f), Color.white);
		accText = draw.addText(player.getAcc().toString(), new Vector2<Float>(100f, 40f), Color.white);
		Text = draw.addText("Text", new Vector2<Float>(100f, 20f), Color.white);
		line = draw.addLine(mousePos, player.getTransform().position(), "");
		circle = draw.addArc(new Vector2<Float>(0f, 0f), 10, 10, "");
		circle.setColor(Color.BLUE);
		circle.setFilled(true);
		
		border.getTransform().position().set(0f, 0f);
		ball.border = border;
		ball.getTransform().position().set(100f, 100f);
		
		Matrix mA = null;
		Matrix mB = null;
			mA = new Matrix(3,2,new float[][] 
					{
				{1, 3},
				{4, -1},
				{-5, 10}
				});
			
			mB = new Matrix(2,2,new float[][] 
					{
				{2, 1},
				{-8, 6}
				});

		if (mA != null && mB != null) {
			Matrix res = Matrix.mult(mA, mB);
//			System.out.println("Matrix A: " + mA.toString());
//			System.out.println("Matrix B: " + mB.toString());
//			System.out.println("Mult: \n" + res.toString());
		}
		
		addGameObjects();
		for (GameObject obj : GameObjects) {
			obj.init();
		}
		cube.rotateX(Math.PI/180*45);
		console.cout("Setup done!");
	}
	
	public void addGameObjects() {
		GameObjects.add(border);
		
		for (int i = 0; i < 10; i++) {
			Ball b = new Ball();
			float x = (float) Math.random() * border.getWIDTH();
			float y = (float) Math.random() * border.getHEIGHT();
//			b.hasGravity(false);
			b.border = border;
			b.getTransform().position().set(x, y);
			GameObjects.add(b);
		}		
		GameObjects.add(cube);
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
		ballCollisonUpdate();
//		gameObjectUpdate();
		sleep((int)(1/144f*1000));
	}
	
	private void gameObjectUpdate() {
		for (GameObject obj : GameObjects) {
			obj.update();
		}
	}
	
	float n = 0;
	private void cubeUpdate() {
		cube.rotateX(Math.PI/180*(n+=0.3f));
	}
	
	float force = 0.9f;
	
	public Vector2<Float> calcF(Vector2<Float> d1, Vector2<Float> d2){
		float dx = d2.getX() - d1.getX();
		float dy = d2.getY() - d1.getY();
		float d = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		//v= a * d
		float a = force / player.getMass();
		return new Vector2<Float>(dx * a, -dy*a);
	}
	
	private void missileUpdate(float force) {
		Vector2<Float> pos = player.getTransform().position();
		velText.setText("FPS: " + (int) (1/Canvas.getInstance().deltaTime()));
		accText.setText("Acceleration: " + player.getAcc().toString());
//		if(pos.getX() > mousePos.getX()) {
//			player.setForce(-force, player.getForce().getY());
//		}else if(pos.getX() < mousePos.getX()) {
//			player.setForce(force, player.getForce().getY());
//		}
//		if(pos.getY() > mousePos.getY()) {
//			player.setForce(player.getForce().getX(), force);
//		}else if(pos.getY() < mousePos.getY()) {
//			player.setForce(player.getForce().getX(), -force);
//		}
		float m = (float) Vector2.getDistance(pos, mousePos);
//		float m = 100;
		float c = 0;
		float multx = c/ (m <= 0 ? 0.001f : m);
		float multy = c/(m <= 0 ? 0.001f : m);
		Text.setText("mx: " + multx + " my: " + multy);
		Vector2<Float> newPos = new Vector2<Float>(-(player.getVel().getX()+player.getAcc().getX()) * multx, -(player.getVel().getY()+player.getAcc().getY()) * multy);
		Vector2<Float> f = calcF(new Vector2<Float>(pos.getX()+15 - newPos.getX(), pos.getY()+15 - newPos.getY()), mousePos);
		player.setForce(f.getX(), f.getY());
		draw.deleteShape(line);
		int mult = 0;
		line = draw.addLine(Vector2.add(new Vector2<Float>(pos.getX()+15, pos.getY()+15), new Vector2<Float>(f.getX() * mult, -f.getY() * mult)), new Vector2<Float>(pos.getX()+15, pos.getY()+15), "");
		Vector2<Float> circlePos = Vector2.add(mousePos, newPos);
//		circle.setPos(new Vector2<Float>(circlePos.getX() - 7.5f/2, circlePos.getY()-7.5f/2));
	}
	
	private void ballCollisonUpdate() {
		for (GameObject obj : GameObjects) {
			if(obj == GameObjects.get(0)) continue;
//			System.out.println("1 and 2: " + String.valueOf(Vector2.getDistance(GameObjects.get(1).getTransform().position(), GameObjects.get(2).getTransform().position())) +
//					" :: 3 and 1: " + String.valueOf(Vector2.getDistance(GameObjects.get(3).getTransform().position(), GameObjects.get(1).getTransform().position())) +
//					" :: 3 and 2: " + String.valueOf(Vector2.getDistance(GameObjects.get(3).getTransform().position(), GameObjects.get(2).getTransform().position())));
			for (GameObject collider : GameObjects) {
//				GameObject collider = GameObjects.get(3);
				if(obj == collider || collider == GameObjects.get(0)) continue;
				float dist = (float) Vector2.getDistance(obj.getTransform().position(), collider.getTransform().position()) - 50f;
				if(dist >= 50f) continue;
				if(dist <= 0) {
					//Collision
					obj.buildingBlocks().getObjects().get(0).setColor(Color.red);
					collider.buildingBlocks().getObjects().get(0).setColor(Color.red);
//					obj.getVel().set(Vector2.scale(new Vector2<Float>(obj.getVel().getX(), -obj.getVel().getY()), -1));
				}else {
					obj.buildingBlocks().getObjects().get(0).setColor(Color.white);				
					collider.buildingBlocks().getObjects().get(0).setColor(Color.white);					
				}
			}
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
