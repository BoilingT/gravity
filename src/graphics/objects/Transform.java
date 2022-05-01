package graphics.objects;

import global.math.Vector2;

public class Transform {
	private Vector2<Float> position;
	private Vector2<Float> rotation;
	private Vector2<Float> scale;
	
	public Vector2<Float> position(){
		return this.position;
	}
	
	public Vector2<Float> rotation(){
		return this.rotation;
	}
	
	public Vector2<Float> scale(){
		return this.scale;
	}
	
	public Transform() {
		this.position = new Vector2<Float>(0f, 0f);
		this.rotation = new Vector2<Float>(0f, 0f);
		this.scale = new Vector2<Float>(1f, 1f);
	}
}
