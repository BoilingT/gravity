package graphics.objects;

import global.math.Vector2;
import global.math.Vector3;

public class animateObject {
	private ShapeObject obj;
	private Vector2<Float> vel;
	private float dx, dy, dt;	

	public Vector3<Float> getDelta(){
		return new Vector3<Float>(dx, dy, dt);
	}
	
	public ShapeObject getObj(){
		return this.obj;
	}
	
	public animateObject(ShapeObject obj, Vector2<Float> origin, Vector2<Float> destination, float dt) {
		if (dt <= 0) { //The time can't be zero or a negative value.
			return;
		}
		
		this.obj = obj;
		this.dt = dt;
		
		this.dy = destination.getY() - origin.getY();
		this.dx = destination.getX() - origin.getX();
		// v = s / dt
		this.vel = new Vector2<Float>(dx/dt, dy/dt);
	}
	
	public void update() {
		this.obj.getPos().set(this.obj.getPos().getX() + this.vel.getX(), this.obj.getPos().getY() + this.vel.getY());
	}

}
