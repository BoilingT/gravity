package graphics.objects;

import global.math.Vector3;

public class Transform {
	private Vector3<Float> position;
	private Vector3<Float> rotation;
	private Vector3<Float> scale;
	
	public Vector3<Float> position(){
		return this.position;
	}
	
	public Vector3<Float> rotation(){
		return this.rotation;
	}
	
	public Vector3<Float> scale(){
		return this.scale;
	}
	
	public Transform() {
		this.position = new Vector3<Float>(0f, 0f, 0f);
		this.rotation = new Vector3<Float>(0f, 0f, 0f);
		this.scale = new Vector3<Float>(1f, 1f, 1f);
	}
}
