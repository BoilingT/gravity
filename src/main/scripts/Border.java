package main.scripts;

import java.awt.Color;

import global.math.Vector2;
import graphics.Shapes;
import graphics.objects.GameObject;

public class Border extends GameObject{

	private float WIDTH, HEIGHT, WEIGHT;
	public float getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(float wIDTH) {
		WIDTH = wIDTH;
	}

	public float getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(float hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public float getWEIGHT() {
		return WEIGHT;
	}

	public void setWEIGHT(float wEIGHT) {
		WEIGHT = wEIGHT;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	private Color color;
	
	public Border(float w, float h, float weight, Color color) {
		this.WIDTH = w;
		this.HEIGHT = h;
		this.WEIGHT = weight;
		this.color = color;
	}
	
	@Override
	public void init() {
		this.addShape(Shapes.Rect(new Vector2<Float>(0f, 0f), WEIGHT, HEIGHT), color, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(0f, HEIGHT-WEIGHT), WIDTH, WEIGHT), color, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(WIDTH-WEIGHT, 0f), WEIGHT, HEIGHT), color, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(0f, 0f), WIDTH, WEIGHT), color, true);
	}

}
