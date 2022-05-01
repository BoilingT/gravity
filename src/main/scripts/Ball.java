package main.scripts;

import java.awt.Color;

import global.math.Vector2;
import graphics.Shapes;
import graphics.objects.GameObject;
import graphics.objects.ShapeObject;

public class Ball extends GameObject{

	public Border border = null;
	
	@Override
	public void init() {
		this.setMass(10f);
		setRandVel();
//		System.out.println(this.getMass());
//		this.hasGravity(false);
		this.addShape(Shapes.Arc(new Vector2<Float>(-25f,-25f), 50f, 50f), Color.white, true);
		this.addShape(Shapes.Arc(new Vector2<Float>(-5f, -5f), 10, 10), Color.white, true);
		this.addShape(Shapes.Rect(new Vector2<Float>(-25f, -25f), 50f, 50f), Color.blue, false);
	}
	
	@Override
	public void update() {
		super.update();
		collisionUpdate();
	}
	
	private void setRandVel() {
		this.getVel().set(((float) Math.random() > 0.7 ? (float) Math.random() : (float) -Math.random()) * 300f,0f);
	}
	
	private void collisionUpdate() {
		if (border != null) {
			Vector2<Float> pos = this.getTransform().position();
			Vector2<Float> borderPos = border.getTransform().position();

			Vector2<Float> v = getVel();
			if(pos.getY() + 25f >= border.getHEIGHT()-borderPos.getY()) {
				if(v.getY() > 0) {
					this.getVel().set(v.getX(), v.getY() * -1 -10);	
				}
			}
			if(pos.getX() + 25f >= border.getWIDTH()+borderPos.getX() || pos.getX() - 25f <= borderPos.getX()) {
				this.getVel().set(v.getX() * -1, v.getY());
			}	
		}
	}

}
