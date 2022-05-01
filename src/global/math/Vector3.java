package global.math;

public class Vector3<T> extends Vector2<T>{
	
	private T x;
	private T y;
	private T z;
	
	public Vector3() {
		this.x = this.y = this.z = null;
	}
	
	public Vector3(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public T getX() {
		return this.x;
	}

	public T getY() {
		return this.y;
	}

	public T getZ() {
		return this.z;
	}
	
	public void set(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3<T> vec3) {
		this.x = vec3.getX();
		this.y = vec3.getY();
		this.z = vec3.getZ();
	}
	
	public String toString() {
		return "[x: " + this.getX() + ", y: " + this.getY() + ", z: " + this.getZ() + "]"; 
	}
	
	
}
