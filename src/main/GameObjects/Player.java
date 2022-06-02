package main.GameObjects;

import java.awt.Color;

import global.math.Vector2;
import graphics.Shapes;
import graphics.objects.GameObject;

public class Player extends GameObject{
	
	public void init() {
		this.setMass(20f);
		this.addShape(Shapes.Rect(new Vector2<Float>(0f, 0f), 30, 30), Color.red, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(50f, 5f), 20, 20), Color.blue, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(-50f+10, 5f), 20, 20), Color.blue, true);
//		this.setForce(1f, 0f);
	}
	
	@Override
	public void update() {
//		if (getTransform().position().getY() < 335) {
//			setForce(1f, -10f); //Gravity
//		}else {
//			setForce(-0.9f, 10f);			
//			
//		}
//		System.out.println("Pos: " + getTransform().position().toString());
//		System.out.println("Acc: " + getAcc().toString());
		super.update();
	}
	
}
